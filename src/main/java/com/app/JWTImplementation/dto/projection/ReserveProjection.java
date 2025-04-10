package com.app.JWTImplementation.dto.projection;

import java.time.LocalDateTime;

import com.app.JWTImplementation.model.Reserve.StatusReserve;

/**
 * SOLUCIÓN IMPLEMENTADA PARA OPTIMIZAR CONSULTAS DE RESERVAS
 * 
 * Se utiliza una interfaz de proyección para obtener solo los datos necesarios
 * en una sola consulta optimizada, evitando los problemas comunes del enfoque tradicional:
 * 
 * 1. **Problema N+1**: En el enfoque tradicional, al usar FetchType.LAZY se generaba
 *    una consulta inicial más múltiples consultas adicionales para cada relación.
 *    
 * 2. **LazyInitializationException**: Error "no session" al acceder a relaciones fuera
 *    del contexto transaccional.
 *    
 * 3. **Sobrecarga de datos**: Se transferían todas las columnas de las entidades aunque
 *    solo necesitábamos algunos campos.
 * 
 * BENEFICIOS DE ESTA SOLUCIÓN:
 * - Consulta única optimizada con JOINs explícitos
 * - Solo se seleccionan los campos realmente necesarios
 * - Elimina el riesgo de LazyInitializationException
 * - Mejor rendimiento al reducir la transferencia de datos
 * - Código más limpio y mantenible
 * 
 * NOTA: Este enfoque es ideal para operaciones de lectura. Para operaciones
 * complejas de escritura/modificación, considerar el uso de entidades completas.
 */
public interface ReserveProjection {
    Integer getId();
    LocalDateTime getDateReserve();
    String getUserFullName();
    String getServiceName();
    LocalDateTime getScheduleStart();
    LocalDateTime getScheduleEnd();
    StatusReserve getStatus();
}
