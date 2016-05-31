# HeteSimPROP
Projecte de Programació orientat a l'ús de l'algorisme HeteSim

## Com funciona HeteSim

Per fer el càlcul del HeteSim, en tots el casos, primer descomposem el path i obtenim les matrius d’adjacència necessaries pel càlcul, posteriorment inserim les matrius intermèdies si el path té longitud senar. Les matrius resultants són guardades en un ArrayList<Matriu> per fer més fàcil la iteració sobre el contigut.

En el cas del càlcul de clausures, les matrius obtingudes de la descomposició es multipliquen des de la posició 0 fins a la meitat per obtenir la matriu left i des de la meitat fins al final per obtenir la matriu right. Després, normalitzem left per files i right per columnes i finalment obtenim la clausura multiplicant ambdues matrius i, només si el path tenia més de dos elements, normalitzem els seus elements aplicant la fòrmula del HeteSim. Es retorna una Matriu amb les rellevàncies entre els tipus de Node indicats pel path. Per tal de fer més eficients les consultes a la mateixa clausura aquesta es guarda a disc per no haver de tornar a calcular-la, existent la possibilitat de forçar que es recalculi. Aquestes clausures també mantenen un booleà que indica si estan actualitzades o no, tot i que no afecta en res al càlcul.

Per fer el càlcul de la relleància entre dos Nodes, s’agafa la fila corresponent al primer Node i es va fent la multiplicació amb les matrius següents (obtenint sempre una altra fila) fins la meitat, després agafem la columna de l’última matriu corresponent al segon Node i l’anem multiplicant amb les matrius anteriors (sempre obtenint una columna) fins la meitat. Posteriorment, es fa la multiplicació entre la fila i columnes resultants i es retorna el valor númeric resultant normalitzat amb la fòrmula del HeteSim.

Per fer el càlcul entre un Node i tots els Node d’un altre tipus, s’agafa la fila corresponent al Node i es va fent la multiplicació amb les matrius següents (obtenint sempre una altra fila) fins la meitat, després es multipliquen les matrius des de la meitat fins al final. Finalment es multiplica la fila per la matriu resultants i s’obté una llista de les rellevàncies entre el Node i tots els Node d’un altre tipus, ordenada decreixentment per rellevància. Finalment, segons si es vol tenir les rellevàncies amb el IDs del Node o bé amb els seus noms es retorna ArrayList<Entry<Double, Integer>> o un ArrayList<Entry<Double, String>>.
