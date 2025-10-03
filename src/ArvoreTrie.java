public class ArvoreTrie {

    // tamanho fixo do alfabeto: 'a'..'z'
    private static final int TAMANHO_ALFABETO = 26;

    // nó da trie
    private static class NoTrie {
        NoTrie[] filhos;
        boolean fimPalavra;

        NoTrie() {
            filhos = new NoTrie[TAMANHO_ALFABETO];
            fimPalavra = false;
        }
    }

    private NoTrie raiz;

    public ArvoreTrie() {
        raiz = new NoTrie();
    }

    // converte caractere 'a'..'z' para índice 0..25
    private int caractereParaIndice(char c) {
        return c - 'a';
    }

    // verifica se a chave contém apenas letras a-z ou A-Z
    private boolean chaveValida(String chave) {
        for (int i = 0; i < chave.length(); i++) {
            char c = Character.toLowerCase(chave.charAt(i));
            int idx = caractereParaIndice(c);
            if (idx < 0 || idx >= TAMANHO_ALFABETO) return false;
        }
        return true;
    }

    // inserção (ignora chaves inválidas)
    public void inserir(String chave) {
        if (chave == null) return;
        if (!chaveValida(chave)) {
            System.out.println("Inserção ignorada: chave contém caracteres fora de a-z -> " + chave);
            return;
        }
        chave = chave.toLowerCase();
        NoTrie no = raiz;
        for (int i = 0; i < chave.length(); i++) {
            int idx = caractereParaIndice(chave.charAt(i));
            if (no.filhos[idx] == null) {
                no.filhos[idx] = new NoTrie();
            }
            no = no.filhos[idx];
        }
        no.fimPalavra = true;
    }

    // busca exata
    public boolean buscar(String chave) {
        if (chave == null) return false;
        if (!chaveValida(chave)) return false;
        chave = chave.toLowerCase();
        NoTrie no = raiz;
        for (int i = 0; i < chave.length(); i++) {
            int idx = caractereParaIndice(chave.charAt(i));
            if (no.filhos[idx] == null) return false;
            no = no.filhos[idx];
        }
        return no != null && no.fimPalavra;
    }

    // remoção pública (retorna true se efetivamente removida)
    public boolean remover(String chave) {
        if (chave == null) return false;
        if (!chaveValida(chave)) return false;
        chave = chave.toLowerCase();
        return removerRecursivo(raiz, chave, 0);
    }

    // helper recursivo para remover; retorna true se o nó atual pode ser removido
    private boolean removerRecursivo(NoTrie no, String chave, int profundidade) {
        if (no == null) return false;

        if (profundidade == chave.length()) {
            if (!no.fimPalavra) return false; // palavra não existe
            no.fimPalavra = false;
            // se não tem filhos, pode remover esse nó (sinalizado pelo true)
            return semFilhos(no);
        }

        int idx = caractereParaIndice(chave.charAt(profundidade));
        if (no.filhos[idx] == null) return false;

        boolean deveRemoverFilho = removerRecursivo(no.filhos[idx], chave, profundidade + 1);

        if (deveRemoverFilho) {
            no.filhos[idx] = null;
            // se nó atual não é fim de palavra e não tem filhos, pode ser removido também
            return !no.fimPalavra && semFilhos(no);
        }
        return false;
    }

    // checa se nó não tem filhos (usa TAMANHO_ALFABETO)
    private boolean semFilhos(NoTrie no) {
        for (int i = 0; i < TAMANHO_ALFABETO; i++) {
            if (no.filhos[i] != null) return false;
        }
        return true;
    }

    // imprime todas as palavras armazenadas
    public void imprimirTodasPalavras() {
        imprimirTodasPalavras(raiz, "");
    }

    private void imprimirTodasPalavras(NoTrie no, String prefixo) {
        if (no == null) return;
        if (no.fimPalavra) {
            System.out.println(prefixo);
        }
        for (int i = 0; i < TAMANHO_ALFABETO; i++) {
            if (no.filhos[i] != null) {
                char ch = (char) ('a' + i);
                imprimirTodasPalavras(no.filhos[i], prefixo + ch);
            }
        }
    }

    // sugestoes de palavras (exemplo pratico)
    public void sugestoesPorPrefixo(String prefixo) {
        if (prefixo == null || !chaveValida(prefixo)) {
            System.out.println("Prefixo inválido!");
            return;
        }

        prefixo = prefixo.toLowerCase();
        NoTrie no = raiz;

        // percorre até o último caractere do prefixo
        for (int i = 0; i < prefixo.length(); i++) {
            int idx = caractereParaIndice(prefixo.charAt(i));
            if (no.filhos[idx] == null) {
                System.out.println("Nenhuma sugestão encontrada para: " + prefixo);
                return;
            }
            no = no.filhos[idx];
        }

        // chama auxiliar para listar todas as palavras a partir desse ponto
        listarSugestoes(no, prefixo);
    }

    private void listarSugestoes(NoTrie no, String prefixo) {
        if (no.fimPalavra) {
            System.out.println(prefixo);
        }
        for (int i = 0; i < TAMANHO_ALFABETO; i++) {
            if (no.filhos[i] != null) {
                char ch = (char) ('a' + i);
                listarSugestoes(no.filhos[i], prefixo + ch);
            }
        }
    }
}
