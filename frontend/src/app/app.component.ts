import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';

// PrimeNG Imports
import { TableModule } from 'primeng/table';
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';
import { SelectModule } from 'primeng/select';
import { TabsModule } from 'primeng/tabs';
import { ToastModule } from 'primeng/toast';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { MessageService, ConfirmationService } from 'primeng/api';

// Models e Services
import { ServidorService } from './core/services/servidor.service';
import { SecretariaService } from './core/services/secretaria.service';
import { Servidor } from './core/models/servidor.model';
import { Secretaria } from './core/models/secretaria.model';
import { idadeRangeValidator } from './core/validators/idade.validator';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    TableModule,
    ButtonModule,
    InputTextModule,
    SelectModule,
    TabsModule,
    ToastModule,
    ConfirmDialogModule
  ],
  providers: [MessageService, ConfirmationService],
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  private readonly fb = inject(FormBuilder);
  private readonly servidorService = inject(ServidorService);
  private readonly secretariaService = inject(SecretariaService);
  private readonly messageService = inject(MessageService);
  private readonly confirmationService = inject(ConfirmationService);

  servidores: Servidor[] = [];
  secretarias: Secretaria[] = [];

  servidorForm!: FormGroup;
  secretariaForm!: FormGroup;

  servidorEditandoId: number | null = null;
  secretariaEditandoId: number | null = null;

  ngOnInit(): void {
    this.iniciarFormularios();
    this.carregarDados();
  }

  private iniciarFormularios(): void {
    this.servidorForm = this.fb.group({
      nome: ['', [Validators.required, Validators.minLength(3)]],
      email: ['', [Validators.required, Validators.email]],
      dataNascimento: ['', [Validators.required, idadeRangeValidator(18, 75)]],
      secretariaId: [null, [Validators.required]]
    });

    this.secretariaForm = this.fb.group({
      nome: ['', [Validators.required, Validators.minLength(3)]],
      sigla: ['', [Validators.required, Validators.maxLength(10)]]
    });
  }

  carregarDados(): void {
    this.secretariaService.listar().subscribe({
      next: (dados) => (this.secretarias = dados),
      error: () => this.mostrarToast('error', 'Erro', 'Falha ao carregar secretarias')
    });

    this.servidorService.listar().subscribe({
      next: (dados) => (this.servidores = dados),
      error: () => this.mostrarToast('error', 'Erro', 'Falha ao carregar servidores')
    });
  }

  // --- Ações de Servidores ---
  salvarServidor(): void {
    if (this.servidorForm.invalid) {
      this.servidorForm.markAllAsTouched();
      this.mostrarToast('warn', 'Atenção', 'Preencha os campos corretamente (idade entre 18 e 75 anos)');
      return;
    }

    const payload: Servidor = this.servidorForm.value;

    if (this.servidorEditandoId) {
      this.servidorService.atualizar(this.servidorEditandoId, payload).subscribe({
        next: () => {
          this.mostrarToast('success', 'Sucesso', 'Servidor atualizado!');
          this.resetarServidorForm();
          this.carregarDados();
        },
        error: (err) => this.mostrarToast('error', 'Erro', err.error?.mensagem || 'Erro ao atualizar')
      });
    } else {
      this.servidorService.salvar(payload).subscribe({
        next: () => {
          this.mostrarToast('success', 'Sucesso', 'Servidor cadastrado!');
          this.resetarServidorForm();
          this.carregarDados();
        },
        error: (err) => this.mostrarToast('error', 'Erro', err.error?.mensagem || 'Erro ao cadastrar')
      });
    }
  }

  editarServidor(servidor: Servidor): void {
    this.servidorEditandoId = servidor.id || null;
    this.servidorForm.patchValue({
      nome: servidor.nome,
      email: servidor.email,
      dataNascimento: servidor.dataNascimento,
      secretariaId: servidor.secretaria?.id || servidor.secretariaId
    });
  }

  excluirServidor(id: number): void {
    this.confirmationService.confirm({
      message: 'Tem certeza que deseja remover este servidor?',
      accept: () => {
        this.servidorService.deletar(id).subscribe({
          next: () => {
            this.mostrarToast('success', 'Sucesso', 'Servidor removido!');
            this.carregarDados();
          },
          error: () => this.mostrarToast('error', 'Erro', 'Erro ao excluir servidor')
        });
      }
    });
  }

  resetarServidorForm(): void {
    this.servidorForm.reset();
    this.servidorEditandoId = null;
  }

  // --- Exportação Personalizada para CSV ---
  exportarCSV(): void {
    if (!this.servidores || this.servidores.length === 0) {
      this.mostrarToast('warn', 'Atenção', 'Não há dados para exportar');
      return;
    }

    const cabecalhos = ['ID', 'Nome', 'E-mail', 'Data Nascimento', 'Secretaria', 'Sigla'];
    
    const linhas = this.servidores.map(s => [
      s.id,
      `"${s.nome || ''}"`,
      `"${s.email || ''}"`,
      `"${s.dataNascimento || ''}"`,
      `"${s.secretaria?.nome || ''}"`,
      `"${s.secretaria?.sigla || ''}"`
    ]);

    const conteudoCSV = '\uFEFF' + [cabecalhos.join(';'), ...linhas.map(l => l.join(';'))].join('\n');
    
    const blob = new Blob([conteudoCSV], { type: 'text/csv;charset=utf-8;' });
    const url = window.URL.createObjectURL(blob);
    const a = document.createElement('a');
    a.href = url;
    a.download = `servidores_${new Date().toISOString().slice(0, 10)}.csv`;
    a.click();
    window.URL.revokeObjectURL(url);
  }

  // --- Ações de Secretarias ---
  salvarSecretaria(): void {
    if (this.secretariaForm.invalid) {
      this.secretariaForm.markAllAsTouched();
      return;
    }

    const payload: Secretaria = this.secretariaForm.value;

    if (this.secretariaEditandoId) {
      this.secretariaService.atualizar(this.secretariaEditandoId, payload).subscribe({
        next: () => {
          this.mostrarToast('success', 'Sucesso', 'Secretaria atualizada!');
          this.resetarSecretariaForm();
          this.carregarDados();
        },
        error: () => this.mostrarToast('error', 'Erro', 'Erro ao atualizar secretaria')
      });
    } else {
      this.secretariaService.salvar(payload).subscribe({
        next: () => {
          this.mostrarToast('success', 'Sucesso', 'Secretaria cadastrada!');
          this.resetarSecretariaForm();
          this.carregarDados();
        },
        error: () => this.mostrarToast('error', 'Erro', 'Erro ao cadastrar secretaria')
      });
    }
  }

  editarSecretaria(sec: Secretaria): void {
    this.secretariaEditandoId = sec.id || null;
    this.secretariaForm.patchValue(sec);
  }

  excluirSecretaria(id: number): void {
    this.confirmationService.confirm({
      message: 'Deseja excluir esta Secretaria? (Apenas se não possuir servidores vinculados)',
      accept: () => {
        this.secretariaService.deletar(id).subscribe({
          next: () => {
            this.mostrarToast('success', 'Sucesso', 'Secretaria removida!');
            this.carregarDados();
          },
          error: () => this.mostrarToast('error', 'Erro', 'Não é possível excluir secretaria vinculada a servidores')
        });
      }
    });
  }

  resetarSecretariaForm(): void {
    this.secretariaForm.reset();
    this.secretariaEditandoId = null;
  }

  private mostrarToast(severity: string, summary: string, detail: string): void {
    this.messageService.add({ severity, summary, detail });
  }
}
