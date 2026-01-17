# Arquitectura de GreatEvents API

## 📐 Descripción General

GreatEvents es una API para gestión de eventos musicales con notificaciones automáticas. Implementa un modelo de eventos con **ciclo de vida** (TENTATIVE → CONFIRMED → RESCHEDULED/CANCELLED) y permite que usuarios **sigan artistas** y **marquen eventos como favoritos**.

## 🏗️ Estructura de Capas

### 1. **Presentation Layer (REST Controllers)**
- **Ubicación**: `controller/`
- **Responsabilidad**: Exposición de endpoints HTTP
- **Componentes**:
  - `AdminController`: Gestión de artistas y eventos (solo admin)
  - `PublicController`: Lectura pública de artistas y eventos
  - `UserController`: Perfil de usuario, seguir artistas, favoritos
  - `AuthController`: Autenticación (login/registro)

### 2. **Application/Service Layer**
- **Ubicación**: `service/`
- **Responsabilidad**: Lógica de negocio y orquestación
- **Servicios**:
  - `EventService`: Gestión de eventos con validación de estado
  - `ArtistService`: CRUD de artistas (admin only)
  - `UserService`: Registro y perfil de usuario
  - `NotificationService`: Disparar notificaciones automáticas
  - `AuthService`: JWT generation y validación

### 3. **Domain Layer**
- **Ubicación**: `model/`, `dto/`
- **Entidades**:
  - `Artist`: Artista musical (creado por admin)
  - `Event`: Evento musical con estados
  - `User`: Usuario del sistema (ADMIN, USER, PUBLIC)
  - `Notification`: Registros de notificaciones enviadas

### 4. **Infrastructure/Data Access**
- **Ubicación**: `repository/`
- **Tecnología**: Spring Data JPA
- **BD**: H2 (dev) / PostgreSQL (prod)
- **Queries**:
  - Eventos confirmados para artista X
  - Próximos eventos (fecha > hoy)
  - Eventos favoritos de usuario

### 5. **Configuration Layer**
- **Ubicación**: `config/`
- **Componentes**:
  - `SecurityConfig`: JWT + roles
  - `JwtProvider`: Token generation/validation
  - `CorsConfiguration`: CORS settings
  - `AuditConfig`: Auditoría de cambios

## 🎯 Modelos de Datos

### Entity Relationships
```
Admin (1) ──────── (N) Artist (1) ──── (N) Event
                                          │
User (1)────── (N) Favorite ──────(N)────┘
               (M)
               
User (1)────── (N) FollowedArtist ──────(1) Artist
```

### Estados de Evento
```
TENTATIVE ──┬─→ CONFIRMED ──┬─→ RESCHEDULED ─→ CONFIRMED
            │               │
            └─→ CANCELLED ←─┴──── (from CONFIRMED only)

Notas:
- Solo eventos TENTATIVE se pueden editar
- CONFIRMED y RESCHEDULED son visibles públicamente
- TENTATIVE nunca se expone vía API pública
```

### Notificaciones Automáticas
```
Triggers:
1. Event confirmed     → Notifica usuarios que lo tienen en favoritos
2. Event rescheduled   → Notifica usuarios que siguen al artista
3. Event cancelled     → Notifica usuarios que lo tienen en favoritos
4. User follows artist → Envía bienvenida + próximos eventos del artista
```

## 🔐 Autenticación y Autorización

### Roles y Permisos
```java
ADMIN
├─ POST /admin/artists          ✅ Crear artista
├─ PUT /admin/artists/{id}      ✅ Editar artista
├─ POST /admin/events           ✅ Crear evento
├─ PUT /admin/events/{id}       ✅ Editar evento (si TENTATIVE)
└─ DELETE /admin/events/{id}    ✅ Eliminar evento

USER
├─ GET /api/users/profile       ✅ Ver perfil
├─ POST /api/users/follow       ✅ Seguir artista
├─ POST /api/users/favorites    ✅ Marcar evento favorito
└─ GET /api/events/upcoming     ✅ Próximos eventos (de artistas seguidos)

PUBLIC (No autenticado)
├─ GET /api/public/artists      ✅ Listar artistas
├─ GET /api/public/events       ✅ Listar eventos (solo CONFIRMED/RESCHEDULED)
└─ GET /api/public/events/{id}  ✅ Detalles de evento
```

### JWT Token Structure
```json
{
  "sub": "user@example.com",
  "role": "USER",
  "userId": 123,
  "exp": 1234567890,
  "iat": 1234567890
}
```

## 🎯 Flujos Principales

### 1. Crear Evento (Admin)
```
POST /admin/events
├─ Validar usuario es ADMIN
├─ Validar artista existe
├─ Validar fecha > hoy
├─ Crear evento con estado TENTATIVE
└─ HTTP 201 + EventDTO

# Nota: Evento no es visible públicamente hasta CONFIRMED
```

### 2. Confirmar Evento (Admin)
```
PUT /admin/events/{id}/confirm
├─ Validar evento existe y es TENTATIVE
├─ Cambiar estado a CONFIRMED
├─ TRIGGER: Notificar a usuarios que lo tienen favorito
├─ TRIGGER: Notificar a usuarios que siguen al artista
└─ HTTP 200

# Nota: Ahora es visible en /api/public/events
```

### 3. Seguir Artista (User)
```
POST /api/users/{userId}/follow/{artistId}
├─ Validar usuario existe
├─ Validar artista existe
├─ Crear FollowedArtist relationship
├─ TRIGGER: Enviar email de bienvenida
├─ TRIGGER: Notificar próximos eventos del artista
└─ HTTP 200
```

### 4. Listar Eventos Públicos (Anyone)
```
GET /api/public/events?page=0&size=20
├─ No requiere JWT
├─ Query: 
│   SELECT * FROM events 
│   WHERE status IN ('CONFIRMED', 'RESCHEDULED')
│   AND date > NOW()
│   ORDER BY date ASC
├─ Retorna Page<EventDTO>
└─ HTTP 200
```

## 🔔 Sistema de Notificaciones

### Notificación Storage
```java
@Entity
class Notification {
    Long id;
    User user;           // A quién se notifica
    Event event;         // Evento relacionado
    String type;         // CONFIRMED, RESCHEDULED, CANCELLED
    LocalDateTime sentAt;
    boolean read;
}
```

### Email Template Examples
```
Subject: "Evento que seguías ha sido confirmado!"

Body:
"El artista {{artist.name}} confirmó su evento:
 {{event.name}} el {{event.date}}
 
 Detalles: {{event.description}}
 
 ¿Lo añadimos a tus favoritos?"
```

## 🧪 Testing

### Test Fixtures
```java
@Test
void shouldNotifyUsersWhenEventConfirmed() {
    // Given
    Event tentativeEvent = Event.builder()
        .status(Status.TENTATIVE)
        .build();
    
    User userFollowing = User.builder()
        .role(Role.USER)
        .build();
    
    // When
    eventService.confirmEvent(tentativeEvent.getId());
    
    // Then
    verify(notificationService, times(1))
        .notifyFollowers(tentativeEvent);
}
```

## 📊 Performance y Escalabilidad

### Índices en BD
```sql
-- Queries de filtrado por estado y fecha
CREATE INDEX idx_event_status_date 
ON events(status, event_date DESC);

-- Queries de búsqueda por artista
CREATE INDEX idx_artist_id ON events(artist_id);

-- Queries de usuario favoritos
CREATE INDEX idx_user_id ON favorites(user_id);
```

### Caching Strategy
```java
@Cacheable("artists")
public List<ArtistDTO> getAllArtists() { ... }

@CacheEvict(value = "artists", allEntries = true)
public ArtistDTO updateArtist(Long id, ...) { ... }
```

### Async Notifications
```
Event Confirmed
  ├─ Guardar notificación en BD (sync)
  └─ Enviar email (async - @Async)
      ├─ Si falla: reintentar con exponential backoff
      └─ Si persiste: loguear y alertar admin
```

## 🚀 Roadmap

### v1.1 (Próximo Sprint)
- [ ] Comments en eventos
- [ ] Rating/Reseñas de eventos
- [ ] Integración con calendarios (iCal)

### v2.0 (Futuro)
- [ ] Mobile app (Flutter)
- [ ] Live streaming de eventos
- [ ] Payment integration (tickets)
- [ ] Microservicios (notification service separado)
