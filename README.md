# Lista de Tareas (To-Do List)

## Conceptos

`RecyclerView`, `Adapter`, `ViewHolder`, listas

## Objetivo

Desarrollar una aplicación de lista de tareas donde se puedan agregar, marcar como completadas y
eliminar tareas.

## Funcionalidades Requeridas

- Agregar nuevas tareas mediante un EditText y botón
- Mostrar lista de tareas en `RecyclerView`
- Marcar/desmarcar tareas como completadas (checkbox)
- Eliminar tareas con deslizamiento (swipe) o botón
- Contador de tareas pendientes

## Estructura de Archivos

```
app/src/main/java/com/tuapp/todolist/
├── MainActivity.kt
├── adapter/
│ └── TareasAdapter.kt
├── model/
│ └── Tarea.kt
└── utils/
└── SwipeToDeleteCallback.kt
app/src/main/res/layout/
├── activity_main.xml
└── item_tarea.xml
```

Retos Adicionales

- [x] Ordenar tareas por fecha de creación
- [] ~~Filtrar tareas (mostrar solo pendientes/completadas)~~
- [] ~~Editar tareas existentes~~
- [x] Persistir datos usando SharedPreferences
