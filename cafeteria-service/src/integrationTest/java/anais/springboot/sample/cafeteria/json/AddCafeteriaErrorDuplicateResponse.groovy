package anais.springboot.sample.cafeteria.json

class AddCafeteriaErrorDuplicateResponse {
    public static String json = """
    {
      "errorCode": "ERROR.CF400",
      "errorMessage": "Cafeteria is already exist: 1",
      "detailMessages": []
    }
    """
}
