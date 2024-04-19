package acme.domain

class Book {
    String title


    static constraints = {
        title blank: false, maxSize: 255
    }
}
