package com.allangomes

import com.allangomes.routes.coordinate.Coordinate
import com.allangomes.routes.models.Route
import com.allangomes.routes.stop.Stop
import com.allangomes.routes.coordinate.EventRepository
import com.allangomes.routes.repositories.RoutesRepository
import com.allangomes.routes.route.RoutesRepository
import com.allangomes.routes.services.IServiceMap
import com.allangomes.routes.services.RouteService
import org.assertj.core.api.Assertions
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit4.SpringRunner
import java.util.*

/**
 * Created by allangomes on 12/12/16.
 */

@RunWith(SpringRunner::class)
@SpringBootTest
open class ApplicationTest {

    @Test
    fun contextLoads() {
    }


    @MockBean
    lateinit var repo: RoutesRepository

    @MockBean
    lateinit var map: IServiceMap

    @MockBean
    lateinit var events: EventRepository

    @Test
    fun CreateTest() {
        val start = Coordinate(-3.9935342, -38.9345876)
        val end = Coordinate(-3.7674197, -38.6653856)
        val route = Route()
        route.name = "Test"
        route.routeDate = Date()
        route.vehicleId = 4
        route.stops = listOf(Stop("start", start), Stop("end", end))

        BDDMockito.given(this.map.getRoute(start, end)).willReturn(listOf(start, end))
        route.path = listOf(start, end)
        BDDMockito.given(this.repo.create(route)).willCallRealMethod()
        val service = RouteService(map, repo, events)

        val routeResult = service.create(route)
        Assertions.assertThat(routeResult.name).isEqualTo("Test")
        Assertions.assertThat(routeResult.path).hasSize(2)

        // setup mock and class component methods
    }
}