
import { createRoot } from 'react-dom/client'
import { Provider } from 'react-redux'
import store from './store/store.js'
import "../node_modules/primereact/resources/themes/lara-light-cyan/theme.css";

import App from './App.jsx'

createRoot(document.getElementById('root')).render(
 <Provider store={store}>
    <App />
  </Provider>
)
