
export class GestioneAllarmeRequest {

    constructor(
            public idAnag: number,
            public idConsumi : number,
            public status: boolean
    ) { }
}