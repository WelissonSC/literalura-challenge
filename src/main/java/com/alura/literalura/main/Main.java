package com.alura.literalura.main;

import com.alura.literalura.model.BooksData;
import com.alura.literalura.model.GutendexResponse;
import com.alura.literalura.repository.BookRepository;
import com.alura.literalura.service.ApiConsumer;
import com.alura.literalura.service.BookService;
import com.alura.literalura.service.ConvertData;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {

    private Scanner sc = new Scanner(System.in);
    private ApiConsumer consumer = new ApiConsumer();
    private final String ADRESS = "https://gutendex.com/books/?search=";
    private ConvertData convert = new ConvertData();


    private final BookRepository repository;
    private final BookService bookService;

    public Main(BookRepository repository, BookService bookService) {
        this.repository = repository;
        this.bookService = bookService;
    }

    public void menu() {

        var option = -1;
        while (option != 0) {
            var menu = "1 - Find a book for title" +
                    "\n2 - List all books " +
                    "\n3 - Find books for language " +
                    "\n4 - List all athors " +
                    "\n5 - authors alive in detterminate year  " +
                    "\n0 - Sair";

            System.out.println(menu);
            option = sc.nextInt();
            sc.nextLine();

            switch (option) {
                case 1:
                    getDataBooks();
                    break;
                case 2:
                    listAllBooksSave();
                    break;
                case 3:
                    findForLanguage();
                    break;
                case 4:
                    listAllAuthors();
                    break;
                case 5:
                    authorsForAge();
                    break;
                case 0:
                    System.out.println("Out...");
                    break;
                default:
                    System.out.println("Opão inválida");
            }
        }

    }


    private BooksData getDataBooks() {
        System.out.println("Type the title of the book");
        var title = sc.nextLine();
        //trocando espaco por +
        var json = consumer.getData(ADRESS + title.replace(" ", "+"));
        //Convertendo a resposta completa no campo results
        GutendexResponse gutendexResponse = convert.GetData(json, GutendexResponse.class);
        //verificar se existe o livro retornando o primeiro livro de results
        if (gutendexResponse.results() != null && !gutendexResponse.results().isEmpty()) {
            BooksData book = gutendexResponse.results().get(0);

            // Formatando os autores
            String autoresFormatados = book.autores().stream()
                    .map(a -> a.nome() + " (" + a.anoNascimento() + "–" + a.anoFalecimento() + ")")
                    .collect(Collectors.joining(", "));

            System.out.println("\n📚 Livro encontrado:");
            System.out.println("📖 Título: " + book.titulo());
            System.out.println("👤 Autor(es): " + autoresFormatados);
            System.out.println("🌐 Idioma(s): " + book.idiomas());
            System.out.println("⬇️ Downloads: " + book.numeroDownloads());

            try {
                bookService.salvarLivroApi(book);
                System.out.println("✅ Livro salvo no banco de dados!");
            } catch (Exception e) {
                System.out.println("❌ Erro ao salvar livro no banco:");
                e.printStackTrace();
            }

            return book;
        } else {
            System.out.println("Book not found");
            return null;
        }
    }


    private void listAllBooksSave() {
        var books = bookService.getAllBooks();

        if (books.isEmpty()) {
            System.out.println("No one book found in data base");
        } else {
            System.out.println("Books in the data bases");
            books.forEach(l -> {
                String authors = l.authors().stream()
                        .map(a -> a.nome() + " (" + a.anoNascimento() + "-" + a.anoFalecimento() + ")")
                        .collect(Collectors.joining(", "));
                System.out.println("\n📘 Título: " + l.title());
                System.out.println("👤 Autor(es): " + authors);
                System.out.println("🌐 Idioma(s): " + l.language());
                System.out.println("⬇️ Downloads: " + l.numeroDownloads());
            });
        }
    }


    private void findForLanguage() {
        System.out.println("Type the language, like(en, pt, fr): ");
        String language = sc.nextLine();

        var books = bookService.getBookForLanguage(language);

        if (books.isEmpty()) {
            System.out.println("📭 No one language found in the data base.");
        } else {
            System.out.println("\n📚 Books in language '" + language + "':");
            books.forEach(l -> {
                String authors = l.authors().stream()
                        .map(a -> a.nome() + " (" + a.anoNascimento() + "-" + a.anoNascimento() + ")")
                        .collect(Collectors.joining(", "));


            System.out.println("\n📘 Título: " + l.title());
            System.out.println("👤 Autor(es): " + authors);
            System.out.println("🌐 Idioma(s): " + l.language());
            System.out.println("⬇️ Downloads: " + l.numeroDownloads());
            });
        }
    }

    private void listAllAuthors() {
        var authors = bookService.getAllAuthors();

        if (authors.isEmpty()) {
            System.out.println("📭 No one authors found in th data bases");
        } else {
            System.out.println("\n👤 Authors salve in data bases:");
            authors.forEach(a -> System.out.println(
                    "- " + a.nome() + " (" +
                            (a.anoNascimento() != null ? a.anoNascimento() : "?") + "_" +
                            (a.anoFalecimento() != null ? a.anoFalecimento() : "?") + ")"
            ));
        }
    }

    private void authorsForAge() {
        System.out.println("Type the year for author: ");
        int year = sc.nextInt();
        sc.nextLine();

        var authors = bookService.getAuthorsAliveIn(year);

        if (authors.isEmpty()) {
            System.out.println("📭 No one authors live found in year " + year + ".");
        } else {
            System.out.println("\n👤 Authors live in year: " + year + ".");
            authors.forEach(a -> System.out.println(
                    "- " + a.nome() + " (" +
                            (a.anoNascimento() != null ? a.anoNascimento() : "?") + "_" +
                            (a.anoFalecimento() != null ? a.anoFalecimento() : "?") + ")"
            ));
        }
    }


}
