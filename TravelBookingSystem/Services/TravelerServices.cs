using Microsoft.Extensions.Options;
using MongoDB.Driver;
using TravelBookingSystem.Data;
using TravelBookingSystem.Models;

namespace TravelBookingSystem.Services
{
    public class TravelerServices
    {
        private readonly IMongoCollection<Traveler> _travelerCollection;

        public TravelerServices(IOptions<DatabaseSettings> settings)
        {
            var mongoClient = new MongoClient(settings.Value.Connection);
            var mongoDb = mongoClient.GetDatabase(settings.Value.DatabaseName);

            _travelerCollection = mongoDb.GetCollection<Traveler>(settings.Value.CollectionName);
        }

        //get all travelers
        public async Task<List<Traveler>> GetAsync() =>await _travelerCollection.Find(_ => true).ToListAsync();

        //get traveler by id
        public async Task<Traveler> GetAsync(string id) =>await _travelerCollection.Find(t => t.Id == id).FirstOrDefaultAsync();

        //add new traveler
        public async Task CreateAsync(Traveler newTraveler) =>await _travelerCollection.InsertOneAsync(newTraveler);

        //update traveler
        public async Task UpdateAsync(string id, Traveler updateTraveler) =>await _travelerCollection.ReplaceOneAsync(t => t.Id == id, updateTraveler);

        //delete traveler
        public async Task RemoveAsync(string id) =>await _travelerCollection.DeleteOneAsync(t => t.Id == id);
    }
}
