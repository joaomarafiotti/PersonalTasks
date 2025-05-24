
# PersonalTasks

Aplicativo Android de gerenciamento de tarefas desenvolvido como avaliação da disciplina de **Programação para Dispositivos Móveis** - IFSP.

## 📱 Descrição

O **PersonalTasks** permite ao usuário:

- Cadastrar novas tarefas.
- Visualizar uma lista de tarefas.
- Editar ou excluir tarefas.
- Visualizar detalhes das tarefas.

O foco deste projeto é aplicar os conceitos de **desenvolvimento para Android**, utilizando:

- Arquitetura **MVC**.
- **Persistência local** com Room.
- **RecyclerView** para exibição de listas.
- **Intents explícitas** para navegação entre telas.
- **Menus** de opções e contexto.

---

## ✅ **Funcionalidades**

- [x] Listar todas as tarefas.
- [x] Adicionar nova tarefa.
- [x] Editar tarefa existente.
- [x] Excluir tarefa.
- [x] Visualizar detalhes de uma tarefa.
- [x] Exibir mensagem quando não há tarefas.
- [x] Persistência local com Room.
- [x] Interface simples e responsiva.

---

## 🚀 **Tecnologias utilizadas**

- [x] **Kotlin** — linguagem principal.
- [x] **Android Studio** — IDE.
- [x] **Room** — persistência de dados.
- [x] **RecyclerView** — exibição de lista.
- [x] **ViewBinding** — ligação segura com Views.
- [x] **Coroutines** — operações assíncronas.
- [x] **Material Components** — estilização.

---

## 📂 **Organização do Projeto**

- **model/** → Entidades, DAO, Database e Conversores.
- **repository/** → Camada de acesso ao banco.
- **adapter/** → Adapter do RecyclerView.
- **controller/** → Activities (Controller do MVC).
- **layout/** → Arquivos XML de interface.
- **menu/** → Menus de opções e contexto.
- **drawable/** → Estilos de botões.

---

## 🛠️ **Como executar o projeto**

1. Clone o repositório:

   ```
   git clone https://github.com/joaomarafiotti/PersonalTasks.git
   ```

2. Abra o projeto no **Android Studio**.

3. Configure o emulador ou conecte um dispositivo físico (mínimo **API 26**).

4. Compile e execute o aplicativo.

---

## 🎥 **Vídeo de execução**

[**Clique aqui para assistir ao vídeo**](video-demo.mp4)
> O vídeo demonstra as principais funcionalidades do app: cadastro, listagem, edição, exclusão e visualização de detalhes.

---

## 💡 **Observações**

- O projeto segue a arquitetura **MVC**, isolando as camadas de Model, View e Controller.
- O uso de **conventional commits** foi aplicado durante todo o desenvolvimento.
- O foco está na **funcionalidade** e **organização** do código, conforme orientações da disciplina.
- O código está **comentado** explicando as principais decisões e implementações.
