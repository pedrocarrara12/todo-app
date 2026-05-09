package todo_aplication.todo_app.entidades;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import todo_aplication.todo_app.enums.Categoria;
import todo_aplication.todo_app.enums.Prioridade;
import todo_aplication.todo_app.enums.StatusTarefa;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity

public class Tarefa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O título é obrigatório")
    @Column(nullable = false)
    private String titulo;

    @Column(length = 1000)
    private String descricao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusTarefa status;



    private LocalDate prazo;

    private LocalDateTime dataCriacao;

    private LocalDateTime dataAtualizacao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Prioridade prioridade;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Categoria categoria;

    private String responsavel;

    public Tarefa(Long id) {
        this.id = id;
    }

    public Tarefa(String titulo, StatusTarefa status, Prioridade prioridade,
                  Categoria categoria,  String descricao, LocalDate prazo,
                  String responsavel) {
        this.titulo = titulo;
        this.status = status;
        this.prioridade = prioridade;
        this.categoria = categoria;
        this.descricao = descricao;
        this.prazo = prazo;
        dataCriacao = LocalDateTime.now();
        this.responsavel = responsavel;
    }

    public Long getId() {
        return id;
    }


    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public StatusTarefa getStatus() {
        return status;
    }

    public void setStatus(StatusTarefa status) {
        this.status = status;
    }

    public LocalDate getPrazo() {
        return prazo;
    }

    public void setPrazo(LocalDate prazo) {
        this.prazo = prazo;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public LocalDateTime getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(LocalDateTime dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

    public Prioridade getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(Prioridade prioridade) {
        this.prioridade = prioridade;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public String getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(String responsavel) {
        this.responsavel = responsavel;
    }
}
