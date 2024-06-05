const validcharsElement = document.querySelector("#validchars");
const validnumberElement = document.querySelector("#validnumber");
const validupperElement = document.querySelector("#validupper");
const submitElement = document.querySelector("#submit");
const passwordElement = document.querySelector("#password");
const passwordCheckElement = document.querySelector("#passwordcheck");
const mismatchElement = document.querySelector("#passwordmismatch");


function isValidChars(text) {
    return text.length <= 20 && text.length >= 8;
}

function isValidNumber(text) {
    const regex = /\d/;
    return regex.test(text);
}

function isValidUpper(text) {
    const regex = /[A-Z]/;
    return regex.test(text);
}

function makeElementValid(el) {
    if (el) {
        el.classList.remove('invalid');
        el.classList.add('valid');
    }
}

function makeElementInvalid(el) {
    if (el) {
        el.classList.remove('valid');
        el.classList.add('invalid');
    }
}

const handler = (event) => {
    const value = passwordElement.value;

    const chars = isValidChars(value);
    const number = isValidNumber(value);
    const upper = isValidUpper(value);

    if (chars) {
        makeElementValid(validcharsElement);
    } else {
        makeElementInvalid(validcharsElement);
    }

    if (number) {
        makeElementValid(validnumberElement);
    } else {
        makeElementInvalid(validnumberElement);
    }

    if (upper) {
        makeElementValid(validupperElement);
    } else {
        makeElementInvalid(validupperElement);
    }

    if (passwordElement.value == passwordCheckElement.value) {
        mismatchElement.hidden = true;
    } else {
        mismatchElement.hidden = false;
    }

    if (chars && number && upper && passwordElement.value == passwordCheckElement.value) {
        makeElementValid(submitElement);
        submitElement.disabled = false;
    } else {
        makeElementInvalid(submitElement);
        submitElement.disabled = true;
    }
};

passwordElement.addEventListener("input", handler);
passwordCheckElement.addEventListener("input", handler);