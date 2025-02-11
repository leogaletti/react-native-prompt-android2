// index.js
import { NativeModules } from "react-native";
const { PromptModule } = NativeModules;

/**
 * Exibe um prompt nativo e retorna uma Promise que resolve com o valor inserido.
 *
 * @param {string} title - Título do prompt.
 * @param {string} [message] - Mensagem opcional.
 * @param {string} [placeholder] - Placeholder opcional.
 * @returns {Promise<string>} - Promise que resolve com o texto inserido ou rejeita se cancelado.
 */
export function prompt(title, message, placeholder) {
	return PromptModule.prompt(title, message, placeholder);
}
