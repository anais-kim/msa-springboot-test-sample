package contracts.cafeteria

org.springframework.cloud.contract.spec.Contract.make {
    description("""
        given: cafeteria with id 999 does not exists.
        when: request for cafeteria with id 999.
        then: return NOT FOUND error message.
        """)
    request {
        method 'GET'
        url '/api/cafeterias/999'
    }
    response {
        status 404
        body("""
            {
              "errorCode": "ERROR.CF404",
              "errorMessage": "Cannot find Cafeteria: 999",
              "detailMessages": []
            }
            """)
        headers {
            header('Content-Type', 'application/json;charset=UTF-8')
        }
    }
}
