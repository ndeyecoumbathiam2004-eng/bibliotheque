const countries = document.getElementById("countries");
const loading = document.getElementById("loading");
const error = document.getElementById("error");
const search = document.getElementById("search");

let allCountries = [];

function afficherPays(data) {
    countries.innerHTML = "";

    data.forEach(country => {
        const div = document.createElement("div");

        div.innerHTML = `
            <h2>${country.name}</h2>
            <p>Capitale : ${country.capital || "Inconnue"}</p>
            <p>Région : ${country.region}</p>
            <img src="${country.flags.png}" alt="Drapeau de ${country.name}">
        `;

        countries.appendChild(div);
    });
}

fetch("https://countries.dev/countries")
    .then(response => {
        if (!response.ok) {
            throw new Error("Erreur lors du chargement des pays");
        }

        return response.json();
    })
    .then(data => {
        console.log(data);

        allCountries = data;

        loading.style.display = "none";

        afficherPays(allCountries);
    })
    .catch(err => {
        console.error("Erreur :", err);

        loading.style.display = "none";
        error.textContent = "Impossible de charger les pays.";
        error.style.display = "block";
    });

search.addEventListener("input", () => {
    const texte = search.value.toLowerCase();

    const resultats = allCountries.filter(country =>
        country.name.toLowerCase().includes(texte)
    );

    afficherPays(resultats);
});