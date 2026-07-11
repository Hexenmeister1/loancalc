# LoancalculatorFront

This project was generated using [Angular CLI](https://github.com/angular/angular-cli) version 21.2.19.

## Development server

To start a local development server, run:

```bash
ng serve
```

Once the server is running, open your browser and navigate to `http://localhost:4200/`. The application will automatically reload whenever you modify any of the source files.

## Code scaffolding

Angular CLI includes powerful code scaffolding tools. To generate a new component, run:

```bash
ng generate component component-name
```

For a complete list of available schematics (such as `components`, `directives`, or `pipes`), run:

```bash
ng generate --help
```

## Building

To build the project run:

```bash
ng build
```

This will compile your project and store the build artifacts in the `dist/` directory. By default, the production build optimizes your application for performance and speed.

## Running unit tests

To execute unit tests with the [Vitest](https://vitest.dev/) test runner, use the following command:

```bash
ng test
```

## Running end-to-end tests

For end-to-end (e2e) testing, run:

```bash
ng e2e
```

Angular CLI does not come with an end-to-end testing framework by default. You can choose one that suits your needs.

## Additional Resources

For more information on using the Angular CLI, including detailed command references, visit the [Angular CLI Overview and Command Reference](https://angular.dev/tools/cli) page.


## Task


1. Develop and example aof the Java Spring Boot application. Use the beast practive in software development to show the architecture and quality code. Add Unit tests and integrations tests. On the client side use Angular, also use the best software developemt practices. Implement all request to the server side with the best practives of REST API. Use H2 DB. The example of tastk:

Bitte entwickle eine Webanwendung, die durch die Eingaben...
• des Darlehensbetrages,
• des Sollzinses (in Prozent),
• der anfänglichen Tilgung (in Prozent) und
• der Zinsbindung (in Jahren)
den Tilgungsplan eines Darlehens mit gleichbleibender Rate erstellen kann.
Diese tabellarische Übersicht zeigt dir, wie ein Tilgungsplan aufgebaut ist:
Tilgung(+) /
Datum               Restschuld          Zinsen          Rate                Auszahlung(-)
30.11.2025          -100.000,00 EUR     0,00 EUR        -100.000,00 EUR     -100.000,00 EUR
31.12.2025          -99.833,34 EUR      176,67 EUR      166,66 EUR          343,33 EUR
31.01.2026          -99.666,38 EUR      176,37 EUR      166,96 EUR          343,33 EUR
31.10.2035          -77.949,76 EUR      138,07 EUR      205,26 EUR          343,33 EUR
30.11.2035          -77.744,14 EUR      137,71 EUR      205,62 EUR          343,33 EUR
Zinsbindungsende    -77.744,14 EUR      18.943,74 EUR   22.255,86 EUR       41.199,60 EUR

(Zinsbindung: 10 Jahre, Sollzins: 2,12%, Anfängliche Tilgung: 2%)

Ein Tilgungsplan besteht aus einer Reihe von Einträgen, wobei der erste die Auszahlung des
Darlehens widerspiegelt.
Jeder (weitere) Eintrag besteht aus einem Datum, der Restschuld, den Zinsen, der Tilgung bzw.
Auszahlung sowie der Rate.
Die Auszahlung soll immer am letzten Tag des aktuellen Monats erfolgen. Die erste Tilgung erfolgt
am letzten Tag des darauffolgenden Monats.
Die Zahlungen eines Tilgungsplans sind monatlich darzustellen.
Zum Ende der Zinsbindung sind die Restschuld sowie die geleisteten Zahlungen von Interesse.
Technische Rahmenbedingungen
Baue ein Angular-Frontend zur Eingabe der Parameter und Darstellung des Tilgungsplans sowie ein
Backend in Java oder TypeScript, das die Berechnungslogik als REST-API bereitstellt.
Teile uns das Ergebnis gern in Form eines Links zu einem GitHub Repository mit.