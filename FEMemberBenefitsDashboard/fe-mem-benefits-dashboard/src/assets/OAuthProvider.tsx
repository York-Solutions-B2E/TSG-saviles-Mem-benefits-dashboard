import { GoogleOAuthProvider } from "@react-oauth/google";

interface OAuthProviderProps {
  children: React.ReactNode;
}

export default function OAuthProvider({ children }: OAuthProviderProps) {
  return (
    <GoogleOAuthProvider clientId="883984443893-2dmlii1eqk9daum3ihnm75ij9kqs80fd.apps.googleusercontent.com">
      {children}
    </GoogleOAuthProvider>
  );
}
