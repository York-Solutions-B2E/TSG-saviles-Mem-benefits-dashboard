import { GoogleOAuthProvider } from "@react-oauth/google";

export default function OAuthProvider({ children }:{children: React.ReactNode}) { //React.ReactNode makes sure that "children" is something react can actually render (like plain JS)
  return (
    <GoogleOAuthProvider clientId="883984443893-2dmlii1eqk9daum3ihnm75ij9kqs80fd.apps.googleusercontent.com">
      {children} {/* Entire app is children in this context */}
    </GoogleOAuthProvider>
  );
}
