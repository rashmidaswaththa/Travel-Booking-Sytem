using Microsoft.AspNetCore.Mvc;
using TravelBookingSystem.Models;
using TravelBookingSystem.Services;

namespace TravelBookingSystem.Controllers
{
    [Route("api/trains")]
    [ApiController]
    public class TrainController : ControllerBase
    {
        private readonly TrainServices _trainServices;

        public TrainController(TrainServices trainServices)
        {
            _trainServices = trainServices;
        }

        // GET: api/trains
        [HttpGet]
        public async Task<ActionResult<IEnumerable<Train>>> GetAllTrains()
        {
            var trains = await _trainServices.GetAllTrainsAsync();
            return Ok(trains);
        }

        // GET: api/trains/{id}
        [HttpGet("{id}")]
        public async Task<ActionResult<Train>> GetTrainById(string id)
        {
            var train = await _trainServices.GetTrainByIdAsync(id);
            if (train == null)
            {
                return NotFound();
            }
            return Ok(train);
        }

        // POST: api/trains
        [HttpPost]
        public async Task<ActionResult<Train>> CreateTrain(Train newTrain)
        {
            await _trainServices.CreateTrainAsync(newTrain);
            return CreatedAtAction(nameof(GetTrainById), new { id = newTrain.Id }, newTrain);
        }

        // PUT: api/trains/{id}
        [HttpPut("{id}")]
        public async Task<IActionResult> UpdateTrain(string id, Train train)
        {
            var existingTrain = await _trainServices.GetTrainByIdAsync(id);
            if (existingTrain == null)
            {
                return NotFound();
            }

            await _trainServices.UpdateTrainAsync(id, train);
            return NoContent();
        }

        // DELETE: api/trains/{id}
        [HttpDelete("{id}")]
        public async Task<IActionResult> DeleteTrain(string id)
        {
            var existingTrain = await _trainServices.GetTrainByIdAsync(id);
            if (existingTrain == null)
            {
                return NotFound();
            }

            await _trainServices.RemoveTrainAsync(id);
            return NoContent();
        }
    }
}
