# Java Web -kurssityö | Vaadin

## Sovelluksen käyttöohje

Sovellus on tarkoitettu maalaussessioiden seuraamiseen. Käyttäjä voi lisätä uuden session valitsemalla kankaan, maaleja, kirjoittamalla muistiinpanoja ja tallentamalla sessiolle keston. Sovellus vaatii kirjautumisen ja käyttöoikeudet on jaettu roolien mukaan.

## Toiminnallisuudet ja käyttöönotto

- **Kirjautuminen**: vaatii olemassa olevan käyttäjätunnuksen ja salasanan. Sovellus luo kaksi user- ja kaksi admin-tason käyttäjää testaukseen. Käyttäjät luodaan automaattisesti ja ne näkyvät tiedostossa `DataInitializer.java`.
- **Päänäkymä (sessions)**: Näytä, lisää, muokkaa ja poista maalaussessioita.
- **Admin-sivu**: Mahdollistaa uusien maalien ja kankaiden lisäämisen, muokkauksen ja poiston (vain ADMIN-roolille).
- **About-näkymä**: Perustiedot sovelluksesta.
- **Hakusuodattimet**: Suodata sessioita otsikon, päivämäärän, maalityypin, maalauspohjan materiaalin ja keston mukaan.

### Tietokanta ja käyttöönotto

- Sovellus vaatii paikallisen MySQL-tietokannan.
- Luo tietokanta nimeltä `artsupplierdb` ennen sovelluksen käynnistämistä, komennolla:
  ```sql
  CREATE DATABASE artsupplierdb;
  ```
- Varmista, että tietokannan käyttäjä on `user1` ja salasana `password1`.
- Nämä on määritetty `application.properties` -tiedostossa.
- Sovellus luo tarvittavat taulut automaattisesti ja lisää testidatan `DataInitializer`- avulla.

---

## Itsearviointi ja arviointikriteerien täyttyminen

### Data ja entiteetit – 5/5

- `PaintingSession` (pääentiteetti)
- `SessionDetails` (1:1 relaatiolla PaintingSessioniin)
- `Canvas` (1:N relaatiolla PaintingSessioniin)
- `Paint` (M:N relaatiolla PaintingSessioniin)
- Kaikille näille entiteeteille on toteutettu luonti, haku, muokkaus ja poisto käyttöliittymästä käsin.

### Suodattimet – 5/5

- Suodatus toteutettu Grid-komponentilla:
  - Otsikko (title)
  - Päivämäärä (date)
  - Kankaan materiaali (`Canvas.material`)
  - Sessioajan kesto (duration)
  - Maalityyppi (`Paint.type`)
- Viisi hakuehtoa, yksi liittyy relaatioentiteettiin.

### Tyylit – 5/5

- Globaali tyylitiedosto (`custom-style.css`)
- Yksittäisten komponenttien tyylit (esim. painikkeet)
- Näkymäkohtaisia tyylejä lisätty Admin-sivulle
- Lumo Utility -luokkien käyttö (esim. `filterBar`)
- Oma luokkapohjainen tyylitiedosto ja sen käyttö (esim. `.pixel-button`)

### Ulkoasu – 5/5

- SPA-rakenne toteutettu Vaadinin `MainLayout`in avulla
- Navigointipalkki ja header näkyvät kaikilla sivuilla
- Footer lisätty pääasetteluun
- Neljä selkeästi eri näkymää:
  - `sessions` (USER ja ADMIN)
  - `admin` (vain ADMIN)
  - `about` (kaikille avoin)
  - `login`

### Autentikointi ja tietoturva – 5/5

- Spring Security otettu käyttöön
- Kirjautumissivu toteutettu
- `User`-entiteetti, jossa roolit (`USER` ja `ADMIN`)
- Käyttöoikeudet rajattu roolien mukaan:
  - USER ja ADMIN: `sessions`-sivu
  - Kaikille avoin: `about`-sivu
  - Vain ADMIN: `admin`-sivu
- Kustomoitu virhesivu, jos käyttäjä yrittää päästä admin-sivulle ilman oikeuksia

### Lisätoiminnallisuudet – 2/6

- Projekti julkaistu GitHubiin
- Käytössä salasanan salaus (BCrypt)

---

## Loppupohdinta

Sovelluksen toteutus auttoi ymmärtämään Vaadin-sovellusten rakenteen, tietokantarakenteiden käytön sekä käyttöoikeuksien hallintaa. Erityisesti CSS-tyylien asettelu Vaadinissa vaati kokeilua, mutta lopputuloksena syntyi retro Admin-näkymä.

Kokonaisuutena projekti kattaa kaikki pakolliset arviointikriteerit ja joitakin lisäominaisuuksia.
