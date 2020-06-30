package server;

import common.Flight;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@Path("/flights")
public class FlightsRepository {

    private Connection connection;

    public void setConnection(String pos) {
        try {
            try {
                Class.forName("org.sqlite.JDBC");
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(FlightsRepository.class.getName()).log(Level.SEVERE, null, ex);
            }
            connection = DriverManager.getConnection("jdbc:sqlite:" + pos);
        } catch (SQLException ex) {
            Logger.getLogger(FlightsRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @GET
    @Path("{flightId}")
    @Produces("application/json")
    public Flight getFlight(@PathParam("flightId") int flightId) {
        return findById(flightId);
    }

    @PUT
    @Path("{flightId}")
    @Consumes("application/json")
    public Response updateFlight(@PathParam("flightId") int flightId, Flight flight) {
        Flight existing = findById(flightId);
        if (existing == null)
            return Response.status(Response.Status.NOT_FOUND).build();

        if (existing.equals(flight))
            return Response.notModified().build();

        update(flightId, flight);
        return Response.ok().build();
    }

    private Flight findById(int id) {
        PreparedStatement stat;
        Flight flight = null;
        try {
            stat = connection.prepareStatement("select * from flight where id = ?");
            stat.setString(1, String.valueOf(id));

            ResultSet rs = stat.executeQuery();
            if (rs.next()) {
                flight = new Flight();
                flight.setId(Integer.parseInt(rs.getString("id")));
                flight.setName(rs.getString("name"));
                Logger.getLogger(FlightsRepository.class.getName()).log(Level.INFO, "Accessed : " + flight);
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(FlightsRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
        return flight;
    }

    private void update(int flightId, Flight flight) {
        PreparedStatement stat;
        try {
            stat = connection.prepareStatement("update flight set name = ? where id = ?");
            stat.setString(1, flight.getName());
            stat.setString(2, String.valueOf(flightId));

            int affectedRow = stat.executeUpdate();
            if (affectedRow != 1)
                throw new RuntimeException();
            Logger.getLogger(FlightsRepository.class.getName()).log(Level.INFO, "Updated : " + flight);
        } catch (Exception ex) {
            Logger.getLogger(FlightsRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
