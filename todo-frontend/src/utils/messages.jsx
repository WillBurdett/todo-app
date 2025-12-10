export function GetStartedMessage() {
  return (
    <p>
      Create a <code>todo</code> to get started!
    </p>
  );
}

export function LoadingMessage() {
  return <p>Loading...</p>;
}

export function ErrorMessage({err}) {
    return <p>Error: {err.message}</p>;
}
