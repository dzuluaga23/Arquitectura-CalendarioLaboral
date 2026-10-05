```mermaid
erDiagram
    TIPO {
        int Id PK "SERIAL"
        varchar Tipo "NOT NULL, Unique"
    }

    CALENDARIO {
        int Id PK "SERIAL"
        date Fecha "NOT NULL, Unique"
        int IdTipo FK "NOT NULL"
        varchar Descripcion "NULL"
    }

    TIPO ||--o{ CALENDARIO : "clasifica"