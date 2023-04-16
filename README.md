# API documentation

## Data read related functions

### Get data count

**GET** `/api/dataCount`

Response:

```json
{
  "code": 200,
  "message": "OK",
  "data": 2
}
```

### Get data list (max 10 per page)

**GET** `/api/data`

Parameters:

- page: Integer, required
- name: String, optional

Response: (example: GET /api/data?page=0&name=Stu)

```json
{
  "code": 200,
  "message": "OK",
  "data": [
    {
      "id": 1,
      "name": "Student Name 1",
      "studentId": 19532801,
      "scores": [
        98.285001,
        92.104919,
        94.184573,
        91.501711,
        96.171609
      ],
      "scoreAvg": 94.449563,
      "actions": [
        0,
        1,
        2,
        1,
        2,
        4,
        3
      ]
    },
    {
      "id": 2,
      "name": "Student Name 2",
      "studentId": 19532802,
      "scores": [
        100,
        100,
        100,
        100,
        100
      ],
      "scoreAvg": 100,
      "actions": [
        0,
        1,
        2,
        3,
        4
      ]
    }
  ]
}
```

### Get single data

**GET** `/api/data/{id}`

Parameters:

- id: Integer, required

Response: (example: GET /api/data/1)

```json
{
  "code": 200,
  "message": "OK",
  "data": {
    "id": 1,
    "name": "Student Name 1",
    "studentId": 19532801,
    "scores": [
      98.285001,
      92.104919,
      94.184573,
      91.501711,
      96.171609
    ],
    "scoreAvg": 94.449563,
    "actions": [
      0,
      1,
      2,
      1,
      2,
      4,
      3
    ]
  }
}
```

## Data write related functions

### Create data

**POST** `/api/data`

JSON parameters:

- studentId: Integer, required
- name: String, required
- scores: Float array, required
- scoreAvg: Float, required
- actions: Integer array, required

Response:

```json
{
  "code": 200,
  "message": "OK",
  "data": null
}
```

### Delete data

**DELETE** `/api/data/{id}`

Parameters:

- id: Integer, required

Response: (example: DELETE /api/data/1)

```json
{
  "code": 200,
  "message": "OK",
  "data": null
}
```
