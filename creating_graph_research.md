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
