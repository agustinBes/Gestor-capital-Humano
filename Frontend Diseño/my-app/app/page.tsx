'use client';

import React from 'react';
import { useRouter } from 'next/navigation';

interface MenuOption {
  id: string;
  title: string;
  route: string;
  icon: React.ReactNode;
}

interface MenuPrincipalProps {
  username?: string;
}

export default function MenuPrincipal({ username = 'admin' }: MenuPrincipalProps) {
  const router = useRouter();

  const options: MenuOption[] = [
    {
      id: 'completar-cuestionario',
      title: 'Completar Cuestionario',
      route: '/completar-cuestionario',
      icon: (
        <svg className="w-10 h-10" fill="none" stroke="currentColor" strokeWidth={1.6} viewBox="0 0 24 24">
          <path strokeLinecap="round" strokeLinejoin="round" d="M9 12h3.75M9 15h3.75M9 18h3.75m3 .75H18a2.25 2.25 0 0 0 2.25-2.25V6.108c0-1.135-.845-2.098-1.976-2.192a48.424 48.424 0 0 0-1.123-.08m-5.801 0c-.065.21-.1.433-.1.664 0 .414.336.75.75.75h4.5a.75.75 0 0 0 .75-.75 2.25 2.25 0 0 0-.1-.664m-5.8 0A2.251 2.251 0 0 1 13.5 2.25H15c1.012 0 1.867.668 2.15 1.586m-5.8 0c-.376.023-.75.05-1.124.08C9.095 4.01 8.25 4.973 8.25 6.108V8.25m0 0H4.875c-.621 0-1.125.504-1.125 1.125v11.25c0 .621.504 1.125 1.125 1.125h9.75c.621 0 1.125-.504 1.125-1.125V9.375c0-.621-.504-1.125-1.125-1.125H8.25Z" />
        </svg>
      ),
    },
    {
      id: 'evaluar-candidato',
      title: 'Evaluar Candidato',
      route: '/evaluar-candidato',
      icon: (
        <svg className="w-10 h-10" fill="none" stroke="currentColor" strokeWidth={1.6} viewBox="0 0 24 24">
          <path strokeLinecap="round" strokeLinejoin="round" d="M15.75 6a3.75 3.75 0 1 1-7.5 0 3.75 3.75 0 0 1 7.5 0ZM4.501 20.118a7.5 7.5 0 0 1 14.998 0A17.933 17.933 0 0 1 12 21.75c-2.676 0-5.216-.584-7.499-1.632Zm12.249-7.868 2.25 2.25 4.5-4.5" />
        </svg>
      ),
    },
    {
      id: 'emitir-orden-merito',
      title: 'Emitir Orden de Mérito',
      route: '/emitir-orden-merito',
      icon: (
        <svg className="w-10 h-10" fill="none" stroke="currentColor" strokeWidth={1.6} viewBox="0 0 24 24">
          <path strokeLinecap="round" strokeLinejoin="round" d="M16.5 18.75h-9m9 0a3 3 0 0 1 3 3h-15a3 3 0 0 1 3-3m9 0v-3.375c0-.621-.503-1.125-1.125-1.125h-.871M7.5 18.75v-3.375c0-.621.504-1.125 1.125-1.125h.872m5.007 0H9.497m5.007 0a7.454 7.454 0 0 1-.982-3.172M9.497 14.25a7.454 7.454 0 0 0 .981-3.172M5.25 4.236c-.982.143-1.954.317-2.916.52A6.003 6.003 0 0 0 7.73 9.728M5.25 4.236V4.5c0 2.108.966 3.99 2.48 5.228M5.25 4.236V2.721C7.456 2.41 9.71 2.25 12 2.25c2.291 0 4.545.16 6.75.47v1.516M7.73 9.728a6.726 6.726 0 0 0 2.748 1.35m8.272-6.842V4.5c0 2.108-.966 3.99-2.48 5.228m2.48-5.492a46.32 46.32 0 0 1 2.916.52 6.003 6.003 0 0 1-5.395 4.972m0 0a6.726 6.726 0 0 1-2.749 1.35m0 0a6.772 6.772 0 0 1-3.044 0" />
        </svg>
      ),
    },
    {
      id: 'gestionar-preguntas',
      title: 'Gestionar Preguntas',
      route: '/gestionar-preguntas',
      icon: (
        <svg className="w-10 h-10" fill="none" stroke="currentColor" strokeWidth={1.6} viewBox="0 0 24 24">
          <path strokeLinecap="round" strokeLinejoin="round" d="M9.879 7.519c1.171-1.025 3.071-1.025 4.242 0 1.172 1.025 1.172 2.687 0 3.712-.203.179-.43.326-.67.442-.745.361-1.45.999-1.45 1.827v.75M21 12a9 9 0 1 1-18 0 9 9 0 0 1 18 0Zm-9 5.25h.008v.008H12v-.008Z" />
        </svg>
      ),
    },
    {
      id: 'gestionar-puestos',
      title: 'Gestionar Puestos',
      route: '/gestionar-puestos',
      icon: (
        <svg className="w-10 h-10" fill="none" stroke="currentColor" strokeWidth={1.6} viewBox="0 0 24 24">
          <path strokeLinecap="round" strokeLinejoin="round" d="M20.25 14.15v4.25c0 1.094-.787 2.036-1.872 2.18-2.087.277-4.216.42-6.378.42s-4.291-.143-6.378-.42c-1.085-.144-1.872-1.086-1.872-2.18v-4.25m16.5 0a2.18 2.18 0 0 0 .75-1.661V8.706c0-1.081-.768-2.015-1.837-2.175a48.114 48.114 0 0 0-3.413-.387m4.5 8.006c-.194.165-.42.295-.673.38A23.978 23.978 0 0 1 12 15.75c-2.648 0-5.195-.429-7.577-1.22a2.016 2.016 0 0 1-.673-.38m0 0A2.18 2.18 0 0 1 3 12.489V8.706c0-1.081.768-2.015 1.837-2.175a48.111 48.111 0 0 1 3.413-.387m7.5 0V5.25A2.25 2.25 0 0 0 13.5 3h-3a2.25 2.25 0 0 0-2.25 2.25v.894m7.5 0a48.667 48.667 0 0 0-7.5 0" />
        </svg>
      ),
    },
  ];

  return (
    <div className="min-h-screen bg-[#BCC9A7] text-[#1D2B1B] flex flex-col items-center justify-center p-6 font-sans">
      {/* Título de la sección */}
      <div className="text-center mb-8">
        <h1 className="text-2xl md:text-3xl font-semibold text-[#1D2B1B] mb-1">
          C@pital Humano
        </h1>
        <p className="text-sm text-[#3A4D35]">
          Bienvenido, <span className="font-medium">{username}</span>
        </p>
      </div>

      {/* Contenedor de botones (Grid adaptable para 5 elementos) */}
      <div className="w-full max-w-3xl grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 gap-5 justify-items-center">
        {options.map((item) => (
          <button
            key={item.id}
            type="button"
            onClick={() => router.push(item.route)}
            className="w-full group flex flex-col items-center justify-center h-36 bg-[#F8F9F7] hover:bg-[#1D2B1B] text-[#1D2B1B] hover:text-[#F8F9F7] border border-[#2C3E29] rounded-lg transition-all duration-200 shadow-sm hover:shadow-md hover:-translate-y-0.5 cursor-pointer p-4 text-center"
          >
            <div className="mb-3 text-[#2C3E29] group-hover:text-[#BCC9A7] group-hover:scale-110 transition-all duration-200">
              {item.icon}
            </div>
            <span className="text-sm font-medium tracking-wide">
              {item.title}
            </span>
          </button>
        ))}
      </div>
    </div>
  );
}