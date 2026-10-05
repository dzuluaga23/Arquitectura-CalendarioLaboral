```mermaid
graph LR
    subgraph Presentacion [Módulo: presentacion]
        ApiApp[CalendarioApplication]
        Controladores[Controladores]
        Configuracion[Configuracion / Swagger]
        Handlers[Handlers de Excepciones]
    end

    subgraph Aplicacion [Módulo: aplicacion]
        ServiciosApp[Servicios de Aplicacion]
    end

    subgraph Core [Módulo: core]
        InterfacesServicio[Interfaces de Servicio]
        InterfacesRepo[Interfaces de Repositorio]
        InterfacesIntegracion[Interfaces de Integracion]
    end

    subgraph Dominio [Módulo: dominio]
        Entidades[Entidades de Dominio]
        DTOs[DTOs]
    end

    subgraph Infraestructura [Módulo: infraestructura]
        direction TB
        RepositoriosImpl[Repositorios Impl]
        RepositoriosJPA[Repositorios JPA]
        EntidadesJPA[Entidades JPA]
        Mapeadores[Mapeadores]
        IntegracionExt[Servicio Externo]
        DB[(PostgreSQL)]
        APIExterna[API Externa Node.js]
    end

    %% Relaciones de Flujo
    Controladores -->|Inyecta| InterfacesServicio
    Controladores -->|Usa| DTOs
    Controladores -->|Usa| Entidades
    
    ServiciosApp -.->|Implementa| InterfacesServicio
    ServiciosApp -->|Inyecta| InterfacesRepo
    ServiciosApp -->|Inyecta| InterfacesIntegracion
    ServiciosApp -->|Maneja| Entidades

    RepositoriosImpl -.->|Implementa| InterfacesRepo
    RepositoriosImpl -->|Inyecta| RepositoriosJPA
    RepositoriosImpl -->|Usa| Mapeadores
    Mapeadores -->|Transforma| Entidades
    Mapeadores -->|Transforma| EntidadesJPA

    RepositoriosJPA -->|JPA / SQL| DB
    EntidadesJPA -->|ORM| DB

    IntegracionExt -.->|Implementa| InterfacesIntegracion
    IntegracionExt -->|GET| APIExterna