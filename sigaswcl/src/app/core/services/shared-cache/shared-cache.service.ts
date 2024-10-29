import {Injectable} from '@angular/core';

@Injectable({
    providedIn: 'root'
})
export class SharedCacheService {

    private cache = new Map<string, any>();

    constructor() {
    }

    /**
     * Inserisce dati in cache, se presente
     * già la chiave indicato il dato sarà
     * sovrascritto.
     *
     * @param key
     * @param data
     */
    public put(key: string, data: any): boolean {
        try {
            this.cache.set(key, data);
            return true;
        } catch (e) {
            return false;
        }
    }

    /**
     * Recupera i dati dalla chache se presenti.
     *
     * @param key
     */
    public get(key: string): any {
        return this.cache.get(key);
    }

    /**
     * Elimina i dati associati alla chiave dalla
     * cache.
     *
     * @param key
     */
    public delete(key: string): boolean {
        return this.cache.delete(key);
    }

    /**
     * Ripulisce la cache.
     */
    public clean() {
        this.cache = new Map<string, any>();
    }
}
