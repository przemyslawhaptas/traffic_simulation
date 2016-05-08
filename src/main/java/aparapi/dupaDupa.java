//public static int[] gibMiFerstAraj(){
//        int [] tab = new int [32];
//        // id pierwszego odcinka, z niego lec
//        tab[0] = 0;
//        //max capacity
//        tab[1] = 10;
//        //free spaces
//        tab[2] = 2;
//        //number of possible outputs
//        tab[3] = 3;
//        //first output id
//        tab[4] = 10;
//        //second output id
//        tab[5] = 11;
//        //third output id
//        tab[6] = 12;
//        tab[7] = -1;
//
//
//        //id drugiego odcinka
//        tab[9] = 10;
//        //max capacity
//        tab[9] = 10;
//        //free spaces
//        tab[10] = 10;
//        //number of possible outputs
//        tab[11] = 3;
//        //first output id
//        tab[12] = 15;
//        //second output id
//        tab[13] = 16;
//        //third output id
//        tab[14] = 17;
//        tab[15] = -1;
//
//
//        //id trzeciego odcinka
//        tab[16] = 11;
//        //max capacity
//        tab[17] = 0;
//        //free spaces
//        tab[18] = 10;
//        //number of possible outputs
//        tab[19] = 3;
//        //first output id
//        tab[20] = 15;
//        //second output id
//        tab[21] = 16;
//        //third output id
//        tab[22] = 17;
//        tab[23] = -1;
//
//        //id czwartego odcinka
//        tab[24] = 12;
//        //max capacity
//        tab[25] = 10;
//        //free spaces
//        tab[26] = 0;
//        //number of possible outputs
//        tab[27] = 3;
//        //first output id
//        tab[28] = 15;
//        //second output id
//        tab[29] = 16;
//        //third output id
//        tab[30] = 17;
//        tab[31] = -1;
//
//        return tab;
//    }
//
//    public void algoSzwed(int[] tab) {
//        int ptr = 0; //currentStreet = getGlobalIndex(); ptr = foo(currentStreet);
//
//        while(ptr < tab.length) {
//            //1. sprawdź, czy twoje free != max_cap (czy masz jakieś samochody)
//            //1.TAK
//            if (tab[ptr+1] != tab[ptr+2]) {
//                //2. sprawdz czy wylosowales juz ulice na ktora chce zjechac
//                //2. NIE
//                if(tab[ptr+8] == -1) { // -1 znaczy ze JESZCZE nie losowano ulicy
//                    int suma_id_outputow = 0;
//                    for(int i = 0; i < tab[ptr+3]; i++)
//                        suma_id_outputow += tab[i];
//                    tab[ptr+8] = tab[ptr+3] + ((suma_id_outputow + tab[ptr+1] - tab[ptr+2]) % tab[ptr+3]); //uber pseudo-random number generator by Nikodem Tokarski
//
//                    //3. sprawdzam czy ulica na ktora chce zjechac ma free > 0
//                    //tab[ptr+8] to pierwszy indeks owej drogi; + 2 i dostajemy sie do indeksu gdzie schowana jest ilosc wolnych miejsc
//                    //3. TAK
//                    if(tab[tab[ptr+8] + 2] > 0) {
//                        //zmiejsz free owej drogi o 1
//                        tab[tab[ptr+8] + 2]--;
//                        //zwieksz swoje free o jeden
//                        tab[ptr+2]++;
//                        //usun ulice z wylosowanych
//                        tab[ptr+8] = -1;
//
//                        ptr  =  ptr  +  9;
//                        //tu ma byc koniec tury
//                    }
//                    //3.NIE
//                    else {
//                        ptr  =  ptr  +  8;
//                        //tu ma byc koniec tury
//                    }
//                }
//                //2. TAK
//                else {
//                    //MAMY WYLOSWANA ULICE
//                    //3. sprawdzam czy ulica na ktora chce zjechac ma free > 0
//                    //tab[ptr+8] to pierwszy indeks owej drogi; + 2 i dostajemy sie do indeksu gdzie schowana jest ilosc wolnych miejsc
//                    //3. TAK
//                    if(tab[tab[ptr+8] + 2] > 0) {
//                        //zmiejsz free owej drogi o 1
//                        tab[tab[ptr+8] + 2]--;
//                        //zwieksz swoje free o jeden
//                        tab[ptr+2]++;
//                        //usun ulice z wylosowanych
//                        tab[ptr+8] = -1;
//
//                        ptr  =  ptr  +  8;
//                        //tu ma byc koniec tury
//                    }
//                    //3.NIE
//                    else {
//                        ptr  =  ptr  +  8;
//                        //tu ma byc koniec tury
//                    }
//                    //tu ma byc koniec tury
//                }
//            }
//            //1.NIE mam aut
//            else {
//                //tu ma byc koniec tury
//            }
//        }
//    }