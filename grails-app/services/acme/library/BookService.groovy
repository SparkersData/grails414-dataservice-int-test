package acme.library

import acme.domain.Book
import acme.library.data.BookDataService
import grails.gorm.transactions.Transactional

@Transactional
class BookService {
    BookDataService bookDataService

    Book updateWithDataService(Book toUpdate) {
        bookDataService.save(toUpdate)
    }

    Book updateWithGORM(Book toUpdate) {
        toUpdate.save(flush: true, failOnError: true)
    }
}
