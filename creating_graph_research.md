Po zbudowaniu grafu:
- węzłami nie są skrzyżowania, a punkty na drogach ustawione tak gęsto, 
  żeby droga między dwoma takimi punktami była prosta oraz inne punkty (niżej przykład)
- tych węzłow jest bardzo dużo - obszar o rozmiarach mniej więcej 50m x 50m 
  (jedno prostopadłe skrzyżowanie) jest zapisany za pomocą 178 węzłów i 178 krawędzi
- w OSM wayami są również linie wysokiego napięcia (tag "cables"), ten graf ich nie usunął ("110kV Prądnik - Łobzów")
- powstaje pytanie, czy jest sens, żeby każda krawędź była jednym wątkiem i miała jakąś przepustowość itd.,
  innymi słowy, czy nie trzeba uprościć tego grafu? Jak to zrobić?
- informacje są z jakiegoś powodu gubione - dla wyżej wymienionego fragmentu Krakowa (skrzyżowania Mazowieckiej
  z Racławicką, przy czym Racławicka jest jednokierunkowa z obu stron w kierunku skrzyżowania) żadna krawędź nie jest oznaczona jako jednokierunkowa
- w zamierzeniu w tym grafie miały być krawędzie jedno- lub dwukierunkowe - czy rozbijalibyśmy te dwukierunowe na dwie różne jednokierunowe w przeciwnych kierunkach?

Przeanalizowałem .osm i doszedłem do wniosku, że potrzebujemy tak zmienić tamten parser, by korzystał tylko z tego fragmentu - to są wszystkie ulice, składają się w sumie z 24 węzłów (choć mogą tu się powtarzać, co by się zgadzało, bo w grafie znalazłem 21 sensownych krawędzi).
``` xml
 <way id="24207680" visible="true" version="22" changeset="37527850" timestamp="2016-02-29T18:54:33Z" user="Sto-Sto" uid="690673">
  <nd ref="226829295"/>
  <nd ref="3997603167"/>
  <nd ref="1642529101"/>
  <nd ref="337767653"/>
  <nd ref="1929453087"/>
  <nd ref="355597626"/>
  <nd ref="355597648"/>
  <nd ref="4033438689"/>
  <nd ref="281472713"/>
  <nd ref="4033435682"/>
  <nd ref="284556682"/>
  <nd ref="1330778497"/>
  <nd ref="338226763"/>
  <nd ref="226830015"/>
  <tag k="highway" v="residential"/>
  <tag k="name" v="Mazowiecka"/>
 </way>
 <way id="151043594" visible="true" version="5" changeset="37527850" timestamp="2016-02-29T18:54:35Z" user="Sto-Sto" uid="690673">
  <nd ref="284546704"/>
  <nd ref="4033435673"/>
  <nd ref="340467176"/>
  <nd ref="4033435674"/>
  <nd ref="1929452917"/>
  <nd ref="4033435680"/>
  <nd ref="281472713"/>
  <tag k="cycleway" v="opposite_lane"/>
  <tag k="highway" v="residential"/>
  <tag k="name" v="Racławicka"/>
  <tag k="oneway" v="yes"/>
  <tag k="oneway:bicycle" v="no"/>
  <tag k="surface" v="asphalt"/>
 </way>
 <way id="118301063" visible="true" version="7" changeset="24937660" timestamp="2014-08-22T17:40:56Z" user="Mateusz Konieczny" uid="1722488">
  <nd ref="284609242"/>
  <nd ref="284608991"/>
  <nd ref="281472713"/>
  <tag k="area" v="no"/>
  <tag k="cycleway" v="opposite_lane"/>
  <tag k="highway" v="residential"/>
  <tag k="name" v="Racławicka"/>
  <tag k="oneway" v="yes"/>
  <tag k="oneway:bicycle" v="no"/>
 </way>
 ```
