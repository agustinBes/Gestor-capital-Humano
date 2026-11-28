import { Children, type ReactNode } from "react";

type Variant = "error" | "success" | "warning" | "info";

type Props = {
  children: ReactNode;
  variant?: Variant;
};

function Icon({ variant }: { variant: Variant }) {
  if (variant === "success") {
    return (
      <svg aria-hidden="true" viewBox="0 0 24 24" width="20" height="20" fill="none">
        <circle cx="12" cy="12" r="9" stroke="currentColor" strokeWidth="2" />
        <path d="m8 12 2.5 2.5L16 9" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round" />
      </svg>
    );
  }

  if (variant === "error") {
    return (
      <svg aria-hidden="true" viewBox="0 0 24 24" width="20" height="20" fill="none">
        <circle cx="12" cy="12" r="9" stroke="currentColor" strokeWidth="2" />
        <path d="m9 9 6 6m0-6-6 6" stroke="currentColor" strokeWidth="2" strokeLinecap="round" />
      </svg>
    );
  }

  if (variant === "warning") {
    return (
      <svg aria-hidden="true" viewBox="0 0 24 24" width="20" height="20" fill="none">
        <path d="M12 3 2.8 19h18.4L12 3Z" stroke="currentColor" strokeWidth="2" strokeLinejoin="round" />
        <path d="M12 9v4m0 3h.01" stroke="currentColor" strokeWidth="2" strokeLinecap="round" />
      </svg>
    );
  }

  return (
    <svg aria-hidden="true" viewBox="0 0 24 24" width="20" height="20" fill="none">
      <circle cx="12" cy="12" r="9" stroke="currentColor" strokeWidth="2" />
      <path d="M12 11v5m0-8h.01" stroke="currentColor" strokeWidth="2" strokeLinecap="round" />
    </svg>
  );
}

export default function FeedbackMessage({ children, variant = "info" }: Props) {
  const content = Children.toArray(children)
    .filter((child): child is string => typeof child === "string")
    .join(" ");
  const noSearchResults =
    variant === "error" &&
    /\(404(?:\s+Not Found)?\).*no se encontraron resultados de b[uú]squeda/i.test(content);
  const displayVariant = noSearchResults ? "warning" : variant;

  return (
    <div
      role={displayVariant === "error" ? "alert" : "status"}
      className={`feedback-message feedback-message--${displayVariant}`}
    >
      <span className="feedback-message__icon">
        <Icon variant={displayVariant} />
      </span>
      <span>{children}</span>
    </div>
  );
}
