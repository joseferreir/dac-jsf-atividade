CREATE TABLE Autor(
	CPF VARCHAR(14) PRIMARY KEY,
	nome VARCHAR(50) NOT NULL UNIQUE,
	email VARCHAR(40) NOT NULL
);
CREATE TABLE Livro(
	ISBN VARCHAR(18) PRIMARY KEY,
	descricao VARCHAR(100) NOT NULL,
	edicao VARCHAR(50) NOT NULL
);
CREATE TABLE AutorLivro(
	ISBNLivro VARCHAR(50) NOT NULL,
	CPFAutor VARCHAR(14) NOT NULL,
	FOREIGN KEY (CPFAutor) REFERENCES Autor (CPF),
	FOREIGN KEY (ISBNLivro) REFERENCES livro (ISBN),
	PRIMARY KEY(ISBNLivro,CPFAutor)
);
REATE TABLE Emprestimo(
	id serial PRIMARY KEY ,
	data DATE,
	cliente VARCHAR(50),	
	isbnlivro VARCHAR(18) NOT NULL UNIQUE,
	situacao VARCHAR(12),
	FOREIGN KEY (ISBNLivro) REFERENCES livro (ISBN)
)
	
	
