# PersonalTasks

Aplicativo Android de gerenciamento de tarefas desenvolvido como avaliação da disciplina de **Programação para Dispositivos Móveis** - IFSP.

## 📱 Descrição

O **PersonalTasks** permite ao usuário:

- Cadastrar novas tarefas.
- Visualizar uma lista de tarefas.
- Editar ou excluir tarefas.
- Visualizar detalhes das tarefas.
- Ver tarefas excluídas.
- Reativar tarefas deletadas.
- Realizar login, registro, logout e redefinição de senha via Firebase.

O foco deste projeto é aplicar os conceitos de **desenvolvimento para Android**, utilizando:

- Arquitetura **MVC**.
- **Persistência local** com Room (Etapa 1).
- **Persistência remota** com Firebase Realtime Database (Etapa 2).
- **Autenticação** com Firebase Authentication.
- **RecyclerView** para exibição de listas.
- **Intents explícitas** para navegação entre telas.
- **Menus** de opções e contexto.

---

## ✅ **Funcionalidades**

- [x] Registro e login com Firebase Authentication.
- [x] Recuperação de senha via Firebase.
- [x] Listar tarefas ativas.
- [x] Adicionar nova tarefa.
- [x] Editar tarefa existente.
- [x] Excluir tarefa (remoção lógica).
- [x] Visualizar detalhes de uma tarefa.
- [x] Listar tarefas excluídas.
- [x] Reativar tarefas excluídas.
- [x] Exibir mensagem quando não há tarefas.
- [x] Persistência com Firebase Realtime Database.
- [x] Interface simples e responsiva.

---

## 🚀 **Tecnologias utilizadas**

- [x] **Kotlin** — linguagem principal.
- [x] **Android Studio** — IDE.
- [x] **Firebase Authentication** — login e segurança.
- [x] **Firebase Realtime Database** — persistência de tarefas.
- [x] **RecyclerView** — exibição de lista.
- [x] **ViewBinding** — ligação segura com Views.
- [x] **Material Components** — estilização.

---

## 📂 **Organização do Projeto**

- **model/** → Entidades de dados (Tarefa).
- **repository/** → Camada de persistência com Firebase.
- **controller/** → Activities (Controller do MVC).
- **adapter/** → Adapter do RecyclerView.
- **helper/** → Mapeamento e suporte Firebase.
- **layout/** → Arquivos XML de interface.
- **menu/** → Menus de opções e contexto.
- **drawable/** → Estilos de botões e ícones.

---

## 🛠️ **Como executar o projeto**

1. Clone o repositório:

   ```bash
   git clone https://github.com/joaomarafiotti/PersonalTasks.git
   ```

2. Abra o projeto no **Android Studio**.

3. Conecte-se com um emulador ou dispositivo físico (**mínimo API 26**).

4. Compile e execute o aplicativo.

5. Para autenticação funcionar:
   - Configure o Firebase com as mesmas credenciais do `google-services.json`.
   - Ative "Email/Password" no Firebase Authentication.
   - Crie a Realtime Database com regras abertas durante testes.

---

## 🎥 **Vídeo de execução Etapa 1**

[**Clique aqui para assistir ao vídeo**](video-demo.mp4)
> O vídeo demonstra as principais funcionalidades do app: cadastro, listagem, edição, exclusão e visualização de detalhes.


https://github.com/user-attachments/assets/a842225c-613d-4a93-a73a-d297cee9eab8

---

## 🎥 **Vídeo de execução Etapa 2**

[**Clique aqui para assistir ao vídeo**](video_demo_firebase.mp4)
> O vídeo demonstra todas as funcionalidades: login, cadastro, redefinição de senha, criação e exclusão de tarefas, e reativação de tarefas excluídas.

https://github.com/user-attachments/assets/29c01e3b-6653-40b9-be43-55f1bca5cf95

---

## 💡 **Observações**

- O projeto segue a arquitetura **MVC**, isolando as camadas de Model, View e Controller.
- O uso de **conventional commits** foi aplicado durante todo o desenvolvimento.
- O código está **comentado**, limpo e bem organizado conforme orientações da disciplina.
- Repositório criado com versão por branches, contendo merge final da `etapa2`.
