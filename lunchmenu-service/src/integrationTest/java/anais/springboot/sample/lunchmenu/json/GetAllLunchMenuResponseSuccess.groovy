package anais.springboot.sample.lunchmenu.json

class GetAllLunchMenuResponseSuccess {
    public static String json = """
    [
      {
        "id": 1,
        "name": "Pizza",
        "cafeteriaId": 1,
        "calorie": 1000
      },
      {
        "id": 2,
        "name": "Champon",
        "cafeteriaId": 2,
        "calorie": 900
      },
      {
        "id": 3,
        "name": "Pho",
        // if you don't want to validate, comment or remove it.
    //    "cafeteriaId": 3,
        "calorie": 700
      }
    ]
    """
}
