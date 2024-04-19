package acme.library.data

import acme.domain.Book
import grails.gorm.services.Service

@Service(Book)
abstract class BookDataService {

    abstract Book save(Book toSave)

    abstract Book get(Long id)
}