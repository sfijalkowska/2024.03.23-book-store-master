function validate() {
    var title = document.getElementById("title");
    var author = document.getElementById("author");
    var isbn = document.getElementById("isbn");
    var price = document.getElementById("price");
    var quantity = document.getElementById("quantity");
    var info = document.getElementById("info");

    price.value = price.value.replace(',', '.');

    var titleRegex = /^.+$/;
    var authorRegex = /^[A-Z][a-z]+\ [A-Z][a-z]+([-\ ][A-Z][a-z]+)?(,(\ )?[A-Z][a-z]+\ [A-Z][a-z]+([-\ ][A-Z][a-z]+)?)*$/;
    var isbnRegex = /^(978|979)-[0-9]{2}-[0-9]{2,6}-[0-9]{1,5}-[0-9]$/;
    var priceRegex = /^[0-9]+\.[0-9]{1,2}$/;
    var quantityRegex = /^[0-9]+$/

    var result = true;
    var infoText = "";

    if(!titleRegex.test(title.value)) {
        title.style.background = "#fac0c0";
        infoText = infoText + "Niepoprawny tytuł<br>";
        result = false;
    } else {
        title.style.background = null;
    }

    if(!authorRegex.test(author.value)) {
        author.style.background = "#fac0c0";
        infoText = infoText + "Niepoprawny autor<br>";
        result = false;
    } else {
        author.style.background = null;
    }

    if(!isbnRegex.test(isbn.value)) {
        isbn.style.background = "#fac0c0";
        infoText = infoText + "Niepoprawny ISBN<br>";
        result = false;
    } else {
        isbn.style.background = null;
    }

    if(!priceRegex.test(price.value)) {
        price.style.background = "#fac0c0";
        infoText = infoText + "Niepoprawna cena<br>";
        result = false;
    } else {
        price.style.background = null;
    }

    if(!quantityRegex.test(quantity.value)) {
        quantity.style.background = "#fac0c0";
        infoText = infoText + "Niepoprawna ilość<br>";
        result = false;
    } else {
        quantity.style.background = null;
    }

    info.innerHTML = infoText;

    return result;
}