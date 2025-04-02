# Survey System

This is a personal REST API that allows me to create multiple-choice or open-ended surveys. I use it as a hobby to share surveys with my friends and collect their feedback.

## create survey
```json
{
  "title": "¿Cuál es tu lenguaje de programación favorito?",
  "options": [
    { "description": "Java" },
    { "description": "Python" }
  ]
}
```
## create vote
```json
{
  "survey_id": 15,
  "option_id": 60
}
```