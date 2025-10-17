import { StrictMode } from 'react' //Just extra checks in dev
import { createRoot } from 'react-dom/client' //allows us to "mount react app"
import './index.css'
import App from './App.tsx'
import OAuthProvider from './assets/OAuthProvider.tsx'

createRoot(document.getElementById('root')!).render( //Mounts entire app into root element in index.html
  <StrictMode>
    <OAuthProvider>
      <App />
    </OAuthProvider>
  </StrictMode>,
)
