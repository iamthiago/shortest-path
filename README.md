network/create

{
  "map":"SP",
  "routes": [
    {
      "origin":"A",
      "destination":"B",
      "distance":10
    },
    {
      "origin":"B",
      "destination":"D",
      "distance":15
    },
    {
      "origin":"A",
      "destination":"C",
      "distance":20
    },
    {
      "origin":"C",
      "destination":"D",
      "distance":30
    },
    {
      "origin":"B",
      "destination":"E",
      "distance":50
    },
    {
      "origin":"D",
      "destination":"E",
      "distance":30
    }
  ]
}

Response Headers:
HTTP/1.1 201 Created

network/shortest

{
  "map":"SP",
  "origin":"A",
  "destination":"D",
  "autonomy":10,
  "gasValue":2.5
}

Response Headers:
HTTP/1.1 200 OK
Content-Type: application/json; charset=utf-8

Response:
{"route":["A","B","D"],"cost":6.25}