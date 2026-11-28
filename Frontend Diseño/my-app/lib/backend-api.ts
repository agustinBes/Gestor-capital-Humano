export const AUTHENTICATED_QUESTIONNAIRE_SESSION_KEY =
  "cuestionario-candidato-autenticado";
export const DEFAULT_DELETE_CONSULTOR_ID = "1";

const JAVA_LONG_MAX_VALUE = "9223372036854775807";

export function isPositiveLongIdentifier(value: string): boolean {
  if (!/^\d+$/.test(value)) return false;

  const normalized = value.replace(/^0+/, "") || "0";
  return (
    normalized !== "0" &&
    (normalized.length < JAVA_LONG_MAX_VALUE.length ||
      (normalized.length === JAVA_LONG_MAX_VALUE.length &&
        normalized <= JAVA_LONG_MAX_VALUE))
  );
}

export type CandidatoDto = {
  id: number;
  nombre: string;
  apellido: string;
  fecha: string;
  mail: string;
  tipo: string;
  documento: string;
  nacionalidad: string;
  genero: string;
  escolaridad: string;
};

export type PuestoCompetenciaDto = {
  nombreCompetencia: string;
  idCompetencia: number;
  ponderacion: number;
};

export type PuestoDtoCreate = {
  codigo: string;
  nombre: string;
  descripcion: string;
  empresa: string;
  caracteristicas: Record<number, number>;
};

export type PuestoDto = {
  idPuesto: number;
  codigo: string;
  nombre: string;
  descripcion: string;
  empresa: string;
  caracteristicas: PuestoCompetenciaDto[];
};

export type TipoCompetencia = "TECNICA" | "ACTITUDINAL" | "GENERICA";

export type FactorDtoSinCompetencia = {
  id: number;
  codigo: string;
  nombre: string;
  descripcion: string;
  orden: number;
};

export type CompetenciaDtoSinFactores = {
  id: number;
  codigo: string;
  nombre: string;
  descripcion: string;
  tipo: TipoCompetencia;
};

export type CompetenciaDto = CompetenciaDtoSinFactores & {
  factores: FactorDtoSinCompetencia[];
};

export type CompetenciaDtoCreate = {
  codigo: string;
  nombre: string;
  descripcion: string;
  tipo: TipoCompetencia;
};

export type PuestoOrdenMeritoDto = {
  id: number;
  codigo: string;
  nombre: string;
  empresa: string;
  candidatos: number;
  evaluacionesCompletadas: number;
};

export type EvaluacionOrdenMeritoDto = {
  idCuestionario: number;
  idCandidato: number;
  nombreCandidato: string;
  fecha: string;
  estado: string;
};

export type FactorDto = {
  id: number;
  codigo: string;
  nombre: string;
  descripcion: string;
  orden: number;
  competencia: CompetenciaDtoSinFactores;
};

export type FactorDtoCreate = {
  id_competencia: number;
  codigo: string;
  nombre: string;
  descripcion: string;
  nroOrden: number;
};

export type OpcionDto = {
  fila: number;
  orden: number;
  descripcion: string;
};

export type OpcionRespuestaDto = {
  id: number;
  nombre: string;
  descripcion: string;
  opciones: OpcionDto[];
};

export type OpcionDtoCreate = {
  fila: number;
  orden: number;
  descripcion: string;
};

export type OpcionRespuestaDtoCreate = {
  nombre: string;
  descripcion: string;
  opciones: OpcionDtoCreate[];
};

export type PreguntaDto = {
  id: number;
  nombre: string;
  pregunta: string;
  descripcion: string;
  respuesta: OpcionRespuestaDto;
  factor: FactorDto;
  ponderaciones: {
    idOpcionRespuesta: number;
    opcion: number;
    puntaje: number;
  }[];
};

export type PreguntaDtoCreate = {
  idFactor: number;
  titulo: string;
  pregunta: string;
  descripcion: string;
  id_opcionRespuesta: number;
  ponderaciones: number[];
};

export type ItemPuestoCompetencia = {
  idCompetencia: number;
  ponderacion: number;
};

export type ItemCuestionarioDto = {
  idPregunta: number;
  titulo: string;
  pregunta: string;
  descripcion: string;
  opcionSeleccionada: number | null;
  opciones: OpcionDto[];
};

export type BloqueDto = {
  numero: number;
  completado: boolean;
  items: ItemCuestionarioDto[];
};

export type CuestionarioDto = {
  idCuestionario: number;
  idCandidato: number;
  idPuesto: number;
  bloques: BloqueDto[];
};

export type EvaluadosDto = {
  apellido: string;
  nombre: string;
  "tipoDocumento de Documento": string;
  "Numero de Documento": string;
  "clave de ingreso": string;
};

export class BackendApiError extends Error {
  constructor(
    message: string,
    readonly status: number,
    readonly statusText: string
  ) {
    super(message);
    this.name = "BackendApiError";
  }
}

export async function backendRequest<T>(
  path: string,
  init: RequestInit = {}
): Promise<T> {
  const response = await fetch(`/api/backend${path}`, {
    ...init,
    cache: "no-store",
  });

  if (!response.ok) {
    const detail = await response.text();
    let message = detail;
    if (detail) {
      try {
        const body: unknown = JSON.parse(detail);
        if (typeof body === "object" && body !== null) {
          const errorBody = body as Record<string, unknown>;
          const backendMessage = errorBody.mensaje ?? errorBody.message ?? errorBody.detail;
          if (typeof backendMessage === "string" && backendMessage.trim()) {
            message = backendMessage;
          }
        }
      } catch {
        // Keep plain-text backend responses as-is.
      }
    }

    throw new BackendApiError(
      message || `El servidor respondió con HTTP ${response.status}.`,
      response.status,
      response.statusText
    );
  }

  const contentType = response.headers.get("content-type") ?? "";
  if (contentType.includes("application/json")) {
    return response.json() as Promise<T>;
  }
  return response.text() as Promise<T>;
}

export function getErrorMessage(error: unknown): string {
  if (error instanceof BackendApiError) {
    const statusText = error.statusText ? ` ${error.statusText}` : "";
    return `(${error.status}${statusText}) ${error.message}`;
  }
  return error instanceof Error ? error.message : "No se pudo completar la solicitud.";
}
