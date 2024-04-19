import acme.domain.Book

// Place your Spring DSL code here
beans = {
    allowedSearchTypes(LinkedHashSet, [
            "Book",
    ] as Set)

    typeToDomainClass(LinkedHashMap, [
            Book      : Book,
    ])

}
