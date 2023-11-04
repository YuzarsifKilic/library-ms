package com.yuzarsif.libraryservice.service;

import com.yuzarsif.libraryservice.client.BookServiceClient;
import com.yuzarsif.libraryservice.dto.AddBookRequest;
import com.yuzarsif.libraryservice.dto.BookIdDto;
import com.yuzarsif.libraryservice.dto.LibraryDto;
import com.yuzarsif.libraryservice.exception.LibraryNotFoundException;
import com.yuzarsif.libraryservice.model.Library;
import com.yuzarsif.libraryservice.repository.LibraryRepository;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class LibraryService {

    private final LibraryRepository libraryRepository;
    private final BookServiceClient bookServiceClient;

    public LibraryService(LibraryRepository libraryRepository, BookServiceClient bookServiceClient) {
        this.libraryRepository = libraryRepository;
        this.bookServiceClient = bookServiceClient;
    }

    public LibraryDto getAllBooksInLibraryById(String id) {
        Library library = libraryRepository.findById(id)
                .orElseThrow(() -> new LibraryNotFoundException("Library could not found by id: " + id));

        LibraryDto libraryDto = new LibraryDto(library.getId(),
                library.getUserBook()
                        .stream()
                        .map(book -> bookServiceClient.getBookById(book).getBody())
                        .collect(Collectors.toList()));
        return libraryDto;
    }

    public LibraryDto createLibrary() {
        Library newLibrary = libraryRepository.save(new Library());
        return new LibraryDto(newLibrary.getId());
    }

    public void addBookToLibrary(AddBookRequest request) {
        BookIdDto bookIdByIsbn = bookServiceClient.getBookByIsbn(request.getIsbn()).getBody();
        String bookId = bookIdByIsbn.getId();

        Library library = libraryRepository.findById(request.getId())
                .orElseThrow(() -> new LibraryNotFoundException("Library could not found by id: " + request.getId()));

        library.getUserBook()
                .add(bookId);

        libraryRepository.save(library);
    }
}
