public class Lista {

    private No inicio, fim;

    public Lista(No inicio, No fim) {
        this.inicio = inicio;
        this.fim = fim;
    }

    public No getInicio() {
        return inicio;
    }

    public void setInicio(No inicio) {
        this.inicio = inicio;
    }

    public No getFim() {
        return fim;
    }

    public void setFim(No fim) {
        this.fim = fim;
    }

    public Lista() {
        this.inicio = null;
        this.fim = null;
    }

    public void insere(int value) {
        No novo = new No(value, null, null);
        if (inicio == null) {
            inicio = fim = novo;
        } else {
            novo.setAnt(fim);
            fim.setProx(novo);
            fim = novo;
        }
    }

    public No getNo(int n) {
        int i = 0;
        No aux = inicio;
        while (i != n && aux != null) {
            i++;
            aux = aux.getProx();
        }
        return aux;
    }

    public void exibir() {
        No aux = inicio;
        while (aux != null) {
            System.out.print(aux.getValor() + " ");
            aux = aux.getProx();
        }
        System.out.println();
    }

    public int getTam()
    {
        int i=0;
        No aux = inicio;
        while (aux!=null)
        {
            i++;
            aux = aux.getProx();
        }
        return i;
    }

    //ORDENAÇÕES ESTUDAS EM SALA

    public void bubbleSort()
    {
        No TL = fim, aux;
        int box;
        while (TL!=inicio)
        {
            aux = inicio;
            while (aux!=TL)
            {
                if(aux.getValor()>aux.getProx().getValor())
                {
                    box = aux.getValor();
                    aux.setValor(aux.getProx().getValor());
                    aux.getProx().setValor(box);
                }
                aux = aux.getProx();
            }
            TL = TL.getAnt();
        }
    }

    public void shellSort()
    {
        int i,j,dist=1,valor,TL = getTam();
        while (dist<TL)
            dist = dist * 3 + 1;
        dist = dist/3;

        while (dist>0)
        {
            for(i=dist;i<TL;i++)
            {
                valor = getNo(i).getValor();
                j = i;
                while (j-dist>=0&&valor<getNo(j-dist).getValor())
                {
                    getNo(j).setValor(getNo(j-dist).getValor());
                    j = j - dist;
                }
                getNo(j).setValor(valor);
            }
            dist = dist/3;
        }

    }

    //ORDENAÇÕES DE LITERATURAS

    public void countingSort()
    {
        int i, j;
        No aux = inicio, maior = aux;
        aux = aux.getProx();

        //acha maior valor
        while (aux!=null)
        {
            if(aux.getValor()>maior.getValor())
                maior = aux;
            aux = aux.getProx();
        }

        //cria o array C com base no maior
        int C [] = new int[maior.getValor()];
        aux = inicio;

        // adiciona a qtde de vezes que o elemento apareceu no respectivo lugar
        while(aux!=null)
        {
            C[aux.getValor()-1]  = C[aux.getValor()-1] + 1;
            aux = aux.getProx();
        }

        //faz a somatória dentro do array
        for(i=1;i<C.length;i++)
            C[i] = C[i] + C[i-1];

        //cria um array do tamanho da lista
        int[] B = new int[getTam()];

        //vai posicionando cada elemento em seu respectivo lugar em B conforme o algoritmo pede
        aux = fim;
        while (aux!=null)
        {
            B[C[aux.getValor()-1]-1] = aux.getValor();
            C[aux.getValor()-1] = C[aux.getValor()-1] - 1;
            aux = aux.getAnt();
        }

        //posiciona na lista de acordo com B
        for (i=0, aux = inicio;aux!=null;i++,aux = aux.getProx())
            aux.setValor(B[i]);
    }

    public void bucketSort()
    {
        No maior=inicio, aux=maior.getProx(),auxB;
        int baldesqtde=1,local;
        //acha maior elemento da lista
        while (aux!=null)
        {
            if (maior.getValor() < aux.getValor())
                maior = aux;
            aux = aux.getProx();
        }
        //acha qtde adequada de baldes
        while (baldesqtde<getTam())
            baldesqtde = 3 * baldesqtde +1;
        baldesqtde = baldesqtde/3;

        Lista[] buckets = new Lista[baldesqtde];

        //inicializa
        for(int i=0;i<buckets.length;i++)
            buckets[i] = new Lista();

        //posiciona NOs nos baldes
        aux = inicio;
        while (aux!=null)
        {
            local = (int) ((aux.getValor()*1.0/ maior.getValor())*(baldesqtde-1));
            buckets[local].insere(aux.getValor());
            aux = aux.getProx();
        }

        //ordena os buckets
        for(int i =0;i< buckets.length;i++)
            buckets[i].bubbleSort();

        //ordena lista original
        aux = inicio;
        for(int i=0;i<buckets.length;i++)
        {
            for (auxB = buckets[i].getInicio();auxB!=null;auxB = auxB.getProx(),aux = aux.getProx())
                aux.setValor(auxB.getValor());
        }
    }

    public void radixSort()
    {
        Lista ele[] = new Lista[10];
        No aux = inicio, auxB;
        int divisor =1, resto, result, cont=0;

        // inicializa vetor
        for(int i=0; i< ele.length;i++)
            ele[i] = new Lista();

        // coloca cada elemento no local do digito menos significativo da vez
        while(aux!=null)
        {
            result = aux.getValor()/divisor;
            if (result==0)
                cont++;
            resto = result%10;
            ele[resto].insere(aux.getValor());
            aux = aux.getProx();
        }
        divisor = divisor * 10;

        // ordena a lista principal e continua o processo
        while (cont!=getTam())
        {
            cont=0;
            aux = inicio;
            for(int i = 0; i< ele.length;i++)
            {
                auxB = ele[i].getInicio();
                while(auxB!=null)
                {
                    aux.setValor(auxB.getValor());
                    aux = aux.getProx();
                    auxB = auxB.getProx();
                }
            }

            for(int i=0; i< ele.length;i++)
                ele[i] = new Lista();
            aux = inicio;
            while(aux!=null)
            {
                result = aux.getValor()/divisor;
                if (result==0)
                    cont++;
                resto = result%10;
                ele[resto].insere(aux.getValor());
                aux = aux.getProx();
            }
            divisor = divisor*10;
        }
    }

    public void combSort()
    {
        No aux;
        int gap, auxI;
        gap = (int) (getTam()/1.3);
        for (;gap>0;gap = (int)(gap/1.3))
        {
            aux = inicio;
            for (int i=0;i+gap<getTam();i++)
            {
                if (aux.getValor()>getNo(i+gap).getValor())
                {
                    auxI = aux.getValor();
                    aux.setValor(getNo(i+gap).getValor());
                    getNo(i+gap).setValor(auxI);
                }
                aux = aux.getProx();
            }
        }
    }

    public void gnomeSort()
    {
        No aux, marcar;
        int a;
        aux = inicio;
        while (aux.getProx()!=null)
        {
            if(aux.getValor()>aux.getProx().getValor())
            {
                a = aux.getValor();
                aux.setValor(aux.getProx().getValor());
                aux.getProx().setValor(a);
            }
            marcar = aux.getProx();
            while (aux.getAnt()!=null&&aux.getValor()<aux.getAnt().getValor())
            {
                a = aux.getValor();
                aux.setValor(aux.getAnt().getValor());
                aux.getAnt().setValor(a);
                aux = aux.getAnt();
            }
            aux = marcar;
        }
    }



}