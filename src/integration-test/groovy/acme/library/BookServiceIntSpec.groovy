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

    private void setup() {
        book = new Book(title: "The Hobbit")
        book.save(flush: true, failOnError: true)
    }

    def "It is able to update a Book without creating a new transaction, by using GORM in the service."() {
        when:
        book.title = updatedTitle
        bookService.updateWithGORM(book)

        then:
        Book.findByTitle(updatedTitle) != null

        where:
        updatedTitle = "Bilbo The Hobbit"
    }

    def "It is NOT able to update a Book without creating a new transaction, if using a Data Service."() {
        when:
        book.title = updatedTitle
        bookService.updateWithDataService(book)

        then:
        Book.findByTitle(updatedTitle) == null

        where:
        updatedTitle = "Bilbo The Hobbit"
    }

    def "It is able to update a Book by creating a new transaction, if using a Data Service."() {
        when:
        book.title = updatedTitle
        Book.withNewTransaction {
            bookService.updateWithDataService(book)
        }

        then:
        Book.findByTitle(updatedTitle) != null

        where:
        updatedTitle = "Bilbo The Hobbit"
    }

/*
    def "It updates a Book"() {
        when:
        book.title = updatedTitle
//        Book.withNewTransaction {
            bookService.update(book)
//        }

        then:
        Book.findByTitle(updatedTitle) != null

        where:
        updatedTitle = "Bilbo The Hobbit"
    }
*/

}
