package com.practical.consultora_rrhh.services.mediator;

import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.practical.consultora_rrhh.dto.response.evaluadosDto;
import com.practical.consultora_rrhh.excepciones.genericas.ColeccionNotFoundException;
import com.practical.consultora_rrhh.excepciones.genericas.ErrorExportException;
import com.practical.consultora_rrhh.interfaces.Exportable;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
@Service
public class ServiceExportacionEvaluados implements  Exportable<List<evaluadosDto>>{

    @Override
    public byte[] exportar(List<evaluadosDto> datos) throws ErrorExportException {
        if(datos==null ||datos.isEmpty()){ throw new ColeccionNotFoundException("No se recibio una lista de datos para generar el archivo");}

        CsvMapper mapper = new CsvMapper();
        CsvSchema csvSchema = mapper.schemaFor(evaluadosDto.class).withHeader();
        try{

            ObjectWriter writer = mapper.writer(csvSchema);
            byte[] csvBytes = writer.writeValueAsBytes(datos);

            byte[] bom = new byte[] { (byte) 0xEF, (byte) 0xBB, (byte) 0xBF };
            ByteArrayOutputStream out = new ByteArrayOutputStream(bom.length + csvBytes.length);
            out.write(bom);
            out.write(csvBytes);

            return out.toByteArray();
        }catch (IOException i)
        {
            throw new ErrorExportException("Fallo la generacion del archivo");

        }
    }
}
