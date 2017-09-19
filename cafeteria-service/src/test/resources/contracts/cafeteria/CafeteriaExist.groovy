package contracts.cafeteria

org.springframework.cloud.contract.spec.Contract.make {
    description("""
        given: cafeteria with id 4 exists.
        when: request for cafeteria with id 4.
        then: return cafeteria information
        """)
    request {
        method 'GET'
        url '/api/cafeterias/4'
    }
    response {
        status 200
        body("""
              {
                "id": 4,
                "name": "SnapSnack",
                "location": "B2"
              }
              """)
        headers {
            header('Content-Type', 'application/json;charset=UTF-8')
        }
    }
}
