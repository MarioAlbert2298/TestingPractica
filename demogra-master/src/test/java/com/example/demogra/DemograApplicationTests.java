package com.example.demogra;

import com.example.demogra.controller.ClientController;
import com.example.demogra.entity.Client;
import com.example.demogra.repo.ClientRepository;
import com.example.demogra.service.ClientService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;




@SpringBootTest
@ExtendWith(MockitoExtension.class) //Se agrega la extencion de Mockito
@TestInstance(Lifecycle.PER_CLASS)//Nos permite configurar el ciclo de vida de las pruebas JUnit 5.
//@TestInstance tiene dos modos. Uno es LifeCycle.PER_METHOD (el predeterminado).
// El otro es LifeCycle.PER_CLASS.
// Este último nos permite pedirle a JUnit que cree solo una instancia de la clase de prueba y la reutilice entre pruebas.


class DemograApplicationTests {

	private MockMvc mockMvc;//MockMVC ofrece una «api fluida» que permite hacer una llamada web con todos los parámetros que sean necesarios y obtener y comprobar la respuesta.

	ObjectMapper objectMapper = new ObjectMapper();
	ObjectWriter objectWriter = objectMapper.writer();

	@InjectMocks //La @InjectMocks anotación intenta crear una instancia de la
	// instancia del objeto de prueba e inyecta campos anotados con @Mock o
	// @Spy en campos privados del objeto de prueba
	private ClientController clientController;

	@Mock //Un Mock es solo un objeto de prueba
	private ClientService clientService;

	@BeforeAll
	void setUp() {
		MockitoAnnotations.openMocks(this);
		//Las Anotaciones Mockito. El método openMocks() devuelve una instancia de
		// AutoClosable que se puede usar para cerrar el recurso después de la prueba.
		// Las Anotaciones Mockito. openMocks(esta) llamada le dice a Mockito que escanee
		// esta instancia de clase de prueba en busca de cualquier campo anotado con la
		// anotación @Mock e inicialice esos campos como simulacros.
		this.mockMvc = MockMvcBuilders.standaloneSetup(clientController).build();
		//MockMvcBuilders receives Controller objects as parameters
		//standaloneSetup(Object... controllers)
		//Build a MockMvc instance by registering one or more @Controller
		// instances and configuring Spring MVC infrastructure programmatically
	}

	Client user1 = new Client(0L,"Luis","MexicoCity");
	Client user2 = new Client(1L,"Mario","USA");
	Client user3 = new Client(2L,"Shirel","Francia");
	//Se crean los objetos
	List<Client> record = new ArrayList<>(Arrays.asList(user1,user2,user3));
	//Se mete en una lista los objetos

	@Test
	public void ContextLoad() throws Exception {

	}

	@Test
	public void GetAllMappingSucess() throws Exception {

		Mockito.when(clientService.getAll()).thenReturn(record);
		//La respuesta corta es que en su ejemplo, el resultado de
		// mock.method() será un valor vacío apropiado para el tipo;
		// mockito usa el direccionamiento indirecto a través de proxy,
		// la intercepción de métodos y una instancia compartida de la
		// clase MockingProgress para determinar si la invocación de un
		// método en un simulacro es para stubing o para reproducir un
		// comportamiento stub existente en lugar de pasar información sobre
		// stubing a través del valor devuelto. de un método burlado.
		//---
		//Obtiene los datos de la interfaz clientService con el metodo getAll()
		//--
		//Los métodos thenReturn() le permiten definir el valor de
		// retorno cuando se llama a un método particular del objeto simulado.

		mockMvc.perform(MockMvcRequestBuilders
						.get("/client/")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(3)))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].name", is(user1.getName())))
				.andExpect(MockMvcResultMatchers.jsonPath("$[1].name", is(user2.getName())))
				.andExpect(MockMvcResultMatchers.jsonPath("$[2].name", is(user3.getName())));
	}

	@Test
	public void GetOneMappingSucess() throws Exception {

		Mockito.when(clientService.getOne(0l)).thenReturn(user1);
		mockMvc.perform(MockMvcRequestBuilders
						.get("/client/0")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$", notNullValue()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.name", is(user1.getName())));
	}

	@Test
	public void PostMappingSucess() throws Exception {

		Mockito.when(clientService.save(user1)).thenReturn(user1);

		String content = objectWriter.writeValueAsString(user1);

		System.out.println(content);

		mockMvc.perform(MockMvcRequestBuilders
						.post("/client/")
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON)
						.content(content))
				.andExpect(status().isCreated());
	}

	@Test
	public void PutMappingSucess() throws Exception {

		Mockito.when(clientService.update(user1)).thenReturn(user1);

		String content = objectWriter.writeValueAsString(user1);

		System.out.println(content);

		mockMvc.perform(MockMvcRequestBuilders
						.put("/client/")
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON)
						.content(content))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.name", is(user1.getName())));
	}

	@Test
	public void DeleteMappingSucess() throws Exception {

		Mockito.when(clientService.delete(0l)).thenReturn(true);
		String content = objectWriter.writeValueAsString(user1);

		System.out.println(content);

		mockMvc.perform(MockMvcRequestBuilders
						.delete("/client/0")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}



}