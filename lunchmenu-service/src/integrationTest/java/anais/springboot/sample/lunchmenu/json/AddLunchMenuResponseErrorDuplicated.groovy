package anais.springboot.sample.lunchmenu.json

class AddLunchMenuResponseErrorDuplicated {
    public static String json = """
    {
      "errorCode": "ERROR.LM400",
      "errorMessage": "LunchMenu is already exist: 1",
      "detailMessages": []
    }
    """
}
