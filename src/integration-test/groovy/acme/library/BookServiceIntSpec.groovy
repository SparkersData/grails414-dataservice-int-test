package acme.library

import acme.domain.Book
import grails.gorm.transactions.Rollback
import grails.testing.mixin.integration.Integration
import spock.lang.Specification
import spock.lang.Subject

@Integration
@Rollback
@Subject(BookService)
class BookServiceIntSpec extends Specification {

    BookService bookService

    Book book

    void setup() {
        book = new Book(title: "The Hobbit")
        book.save(flush: true, failOnError: true)
    }

    void cleanup() {
        Book.where {}.deleteAll()
    }
    // tag::updateWithGORM[]
    def "It is able to update a Book without creating a new transaction, by using GORM in the service."() {
        when:
        book.title = updatedTitle
        bookService.updateWithGORM(book)

        then:
        Book.findByTitle(updatedTitle) != null

        where:
        updatedTitle = "Bilbo The Hobbit"
    }
    // end::updateWithGORM[]

    // tag::updateWithDataService[]
    def "It is NOT able to update a Book without creating a new transaction, if using a Data Service."() {
        when:
        book.title = updatedTitle
        bookService.updateWithDataService(book)

        then:
        Book.findByTitle(updatedTitle) == null //<1>

        where:
        updatedTitle = "Bilbo The Hobbit"
    }
    // end::updateWithDataService[]

    // tag::updateWithDataServiceAndNewTransaction[]
    def "It is able to update a Book by creating a new transaction, if using a Data Service."() {
        when:
        book.title = updatedTitle
        Book.withNewTransaction { //<1>
            bookService.updateWithDataService(book)
        }

        then:
        Book.findByTitle(updatedTitle) != null //<2>

        where:
        updatedTitle = "Bilbo The Hobbit"
    }
    // end::updateWithDataServiceAndNewTransaction[]
}
