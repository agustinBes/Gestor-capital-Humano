export const AUTHENTICATED_QUESTIONNAIRE_SESSION_KEY =
  "cuestionario-candidato-autenticado";

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

export type PuestoDto = {
  idPuesto: number;
  codigo: string;
  nombre: string;
  descripcion: string;
  empresa: string;
  caracteristicas: PuestoCompetenciaDto[];
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
  competencia: {
    id: number;
    codigo: string;
    nombre: string;
    descripcion: string;
    tipo: string;
  };
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
    throw new Error(detail || `Error del backend (${response.status})`);
  }

  const contentType = response.headers.get("content-type") ?? "";
  if (contentType.includes("application/json")) {
    return response.json() as Promise<T>;
  }
  return response.text() as Promise<T>;
}

export function getErrorMessage(error: unknown): string {
  return error instanceof Error ? error.message : "No se pudo completar la solicitud.";
}
